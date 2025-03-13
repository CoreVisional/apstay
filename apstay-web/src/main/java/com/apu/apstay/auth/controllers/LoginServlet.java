package com.apu.apstay.auth.controllers;

import com.apu.apstay.utils.RequestParameterProcessor;
import com.apu.apstay.auth.models.inputmodels.LoginInputModel;
import com.apu.apstay.auth.models.viewmodels.SessionUserViewModel;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.services.AuthenticationService;
import com.apu.apstay.services.UserService;
import com.apu.apstay.services.UserProfileService;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author alexc
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    @EJB
    private AuthenticationService authService;
    
    @EJB
    private UserService userService;
    
    @EJB
    private UserProfileService userProfileService;

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
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
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

        var loginInput = new LoginInputModel();
        
        Map<String, String[]> processedParams = RequestParameterProcessor.getProcessedParameters(request);
        
        try {
            BeanUtils.populate(loginInput, processedParams);
        } catch (IllegalAccessException | InvocationTargetException e) {
            setErrorAndForward(request, response, "An error occurred during login.");
            return;
        }

        try {
            if (authService.login(loginInput.getLoginKey(), loginInput.getPassword())) {
                var user = userService.getByLoginKey(loginInput.getLoginKey());
                var userProfile = userProfileService.getByUserId(user.id());
                var sessionUser = new SessionUserViewModel(user, userProfile);
                request.getSession().setAttribute("user", sessionUser);

                var redirectPath = authService.determineRedirectPath(loginInput.getLoginKey());
                response.sendRedirect(request.getContextPath() + redirectPath);
                return;
            }
            setErrorAndForward(request, response, "Invalid username or password.");
            
        } catch (BusinessRulesException e) {
            setErrorAndForward(request, response, e.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Login Servlet handles user authentication";
    }
    
    private void setErrorAndForward(HttpServletRequest request, HttpServletResponse response, String message) 
            throws ServletException, IOException {
        request.setAttribute("notice", message);
        request.setAttribute("noticeBg", "alert-danger");
        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }
    
    // </editor-fold>
}
