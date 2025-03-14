package com.apu.apstay.staff.manager.controllers.staffs;

import com.apu.apstay.services.UserService;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author alexc
 */
@WebServlet(name = "ReactivateServlet", urlPatterns = {"/manager/staffs/reactivate"})
public class ReactivateServlet extends HttpServlet {

    @EJB
    private UserService userService;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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

        var idParam = request.getParameter("id");
        
        try {
            var staffId = Long.parseLong(idParam);

            userService.reactivateUser(staffId);
            
            request.getSession().setAttribute("success", "Staff reactivated successfully");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Invalid parameters provided.");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Error reactivating staff: " + e.getMessage());
        }
        
        response.sendRedirect(request.getContextPath() + "/manager/staffs");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>
}
