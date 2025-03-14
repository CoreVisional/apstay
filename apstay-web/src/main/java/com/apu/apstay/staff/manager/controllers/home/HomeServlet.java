package com.apu.apstay.staff.manager.controllers.home;

import com.apu.apstay.services.AccountRegistrationService;
import com.apu.apstay.staff.manager.models.viewmodels.home.ManagerHomeViewModel;
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
@WebServlet(name = "ManagerHomeServlet", urlPatterns = {"/manager"})
public class HomeServlet extends HttpServlet {

    @EJB
    private AccountRegistrationService accountRegistrationService;
    
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
        
        request.setAttribute("activeNav", "home");

        int pendingApprovals = accountRegistrationService.getPendingRegistrationsCount();

        var vm = new ManagerHomeViewModel(pendingApprovals);

        request.setAttribute("dashboard", vm);
        request.getRequestDispatcher("/WEB-INF/views/manager/home/index.jsp").forward(request, response);
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
