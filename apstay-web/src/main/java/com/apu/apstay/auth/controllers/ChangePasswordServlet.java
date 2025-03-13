package com.apu.apstay.auth.controllers;

import com.apu.apstay.auth.models.inputmodels.ChangePasswordInputModel;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.security.SessionContext;
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
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.cdi.HibernateValidator;

/**
 *
 * @author alexc
 */
@WebServlet(name = "ChangePasswordServlet", urlPatterns = {"/manager/profile/change-password", "/security/profile/change-password", "/resident/profile/change-password"})
public class ChangePasswordServlet extends HttpServlet {

    @Inject
    @HibernateValidator
    private Validator validator;
    
    @Inject
    private SessionContext sessionContext;
    
    @EJB
    private UserService userService;
    
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
        
        var originalPath = request.getServletPath();
        request.setAttribute("formAction", originalPath);
        request.setAttribute("backUrl", getProfilePath(request));
        request.getRequestDispatcher("/WEB-INF/views/auth/change-password.jsp").forward(request, response);
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
            var input = new ChangePasswordInputModel();
            Map<String, String[]> processedParams = RequestParameterProcessor.getProcessedParameters(request);
            BeanUtils.populate(input, processedParams);

            var violations = validator.validate(input);
            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<ChangePasswordInputModel> violation : violations) {
                    var propertyPath = violation.getPropertyPath().toString();
                    var message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.setAttribute("errors", errors);
                doGet(request, response);
                return;
            }

            if (!input.getNewPassword().equals(input.getConfirmPassword())) {
                Map<String, String> errors = new HashMap<>();
                errors.put("confirmPassword", "Passwords do not match");
                request.setAttribute("errors", errors);
                doGet(request, response);
                return;
            }

            userService.changePassword(sessionContext.getCurrentUserId(), input.getCurrentPassword(), input.getNewPassword());

            request.getSession().setAttribute("notice", "Password changed successfully");
            request.getSession().setAttribute("noticeBg", "alert-success");
            
            response.sendRedirect(request.getContextPath() + getProfilePath(request));
            
        } catch (BusinessRulesException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getErrorKey(), e.getMessage());
            request.setAttribute("errors", errors);
            doGet(request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            request.setAttribute("notice", "An error occurred while processing your request");
            request.setAttribute("noticeBg", "alert-danger");
            doGet(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Change Password Servlet";
    }
    
    private String getProfilePath(HttpServletRequest request) {
        var servletPath = request.getServletPath();

        if (servletPath.startsWith("/manager")) {
            return "/manager/profile";
        } else if (servletPath.startsWith("/security")) {
            return "/security/profile";
        } else {
            return "/resident/profile";
        }
    }
    // </editor-fold>
}
