package com.apu.apstay.auth.controllers;

import com.apu.apstay.auth.models.inputmodels.ResetPasswordInputModel;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.services.TokenService;
import com.apu.apstay.services.UserService;
import com.apu.apstay.utils.RequestParameterProcessor;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.cdi.HibernateValidator;

/**
 *
 * @author alexc
 */
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/reset-password"})
public class ResetPasswordServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(ResetPasswordServlet.class.getName());
    
    @Inject
    @HibernateValidator
    private Validator validator;
    
    @EJB
    private UserService userService;
    
    @EJB
    private TokenService tokenService;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        
        if (token == null || token.isEmpty()) {
            request.getSession().setAttribute("error", "Invalid password reset link");
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            tokenService.validatePasswordResetToken(token);

            request.setAttribute("token", token);
            request.getRequestDispatcher("/WEB-INF/views/auth/reset-password.jsp").forward(request, response);
        } catch (BusinessRulesException e) {
            request.getSession().setAttribute("error", e.getMessage());
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            var tokenString = request.getParameter("token");
            if (tokenString == null || tokenString.trim().isEmpty()) {
                request.getSession().setAttribute("error", "Invalid password reset attempt");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            var input = new ResetPasswordInputModel();
            Map<String, String[]> processedParams = RequestParameterProcessor.getProcessedParameters(request);
            BeanUtils.populate(input, processedParams);

            var violations = validator.validate(input);
            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<ResetPasswordInputModel> violation : violations) {
                    var propertyPath = violation.getPropertyPath().toString();
                    var message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.setAttribute("errors", errors);
                request.setAttribute("token", tokenString);
                request.getRequestDispatcher("/WEB-INF/views/auth/reset-password.jsp").forward(request, response);
                return;
            }

            // Check if passwords match
            if (!input.getPassword().equals(input.getConfirmPassword())) {
                Map<String, String> errors = new HashMap<>();
                errors.put("confirmPassword", "Passwords do not match");
                request.setAttribute("errors", errors);
                request.setAttribute("token", tokenString);
                request.getRequestDispatcher("/WEB-INF/views/auth/reset-password.jsp").forward(request, response);
                return;
            }

            // Validate token and get user
            var token = tokenService.validatePasswordResetToken(tokenString);

            userService.resetPassword(token.getUser(), input.getPassword());

            tokenService.markTokenAsUsed(token);

            request.getRequestDispatcher("/WEB-INF/views/auth/password-changed.jsp").forward(request, response);
        } catch (BusinessRulesException e) {
            logger.log(Level.WARNING, "Business rule error during password reset", e);
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getErrorKey(), e.getMessage());
            request.setAttribute("errors", errors);
            request.setAttribute("token", request.getParameter("token"));
            request.getRequestDispatcher("/WEB-INF/views/auth/reset-password.jsp").forward(request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.log(Level.SEVERE, "Error during password reset", e);
            request.getSession().setAttribute("error", "An error occurred while processing your request");
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Reset Password Servlet";
    }
    // </editor-fold>
}
