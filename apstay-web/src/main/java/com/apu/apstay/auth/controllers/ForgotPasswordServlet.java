package com.apu.apstay.auth.controllers;

import com.apu.apstay.services.TokenService;
import com.apu.apstay.services.UserService;
import com.apu.apstay.services.mail.EmailService;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexc
 */
@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(ForgotPasswordServlet.class.getName());
    
    @EJB
    private UserService userService;
    
    @EJB
    private TokenService tokenService;
    
    @EJB
    private EmailService emailService;
    
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

        request.getRequestDispatcher("/WEB-INF/views/auth/forgot-password.jsp").forward(request, response);
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
        
        var email = request.getParameter("email");
        
        if (email == null || email.isEmpty()) {
            request.setAttribute("errors", "Please enter your email address");
            doGet(request, response);
            return;
        }
        
        try {
            var user = userService.findByEmail(email);
            
            if (user != null) {
                var token = tokenService.createPasswordResetToken(user);

                var resetLink = request.getScheme() + "://" + request.getServerName() + 
                                 (request.getServerPort() != 80 ? ":" + request.getServerPort() : "") +
                                 request.getContextPath() + "/reset-password?token=" + token;

                emailService.sendPasswordResetEmail(email, resetLink);
            }

            request.getSession().setAttribute("notice", "Password reset instructions have been sent to your email");
            request.getSession().setAttribute("noticeBg", "alert-success");
            response.sendRedirect(request.getContextPath() + "/login");
            
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error processing forgot password request", e);

            request.getSession().setAttribute("notice", "Password reset instructions have been sent to your email");
            request.getSession().setAttribute("noticeBg", "alert-success");
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
        return "Forgot Password Servlet";
    }
    // </editor-fold>
}
