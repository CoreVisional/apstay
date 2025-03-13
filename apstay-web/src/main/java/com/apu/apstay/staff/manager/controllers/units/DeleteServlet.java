package com.apu.apstay.staff.manager.controllers.units;

import com.apu.apstay.services.UnitService;
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
@WebServlet(name = "UnitDeleteServlet", urlPatterns = {"/manager/units/delete"})
public class DeleteServlet extends HttpServlet {

    @EJB
    private UnitService unitService;
    
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
            var id = Long.parseLong(idParam);
            var deleted = unitService.deleteUnit(id);
            
            if (deleted) {
                request.getSession().setAttribute("notice", "Unit has been deleted successfully.");
            } else {
                request.getSession().setAttribute("error", 
                    "Unit cannot be deleted because it has residents assigned to it. " +
                    "Please reassign these residents to different units first from the Residents section.");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("error", "Invalid unit ID format");
        }
        
        response.sendRedirect(request.getContextPath() + "/manager/units");
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
