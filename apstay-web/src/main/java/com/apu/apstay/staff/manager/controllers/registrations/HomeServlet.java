package com.apu.apstay.staff.manager.controllers.registrations;

import com.apu.apstay.enums.ApprovalStatus;
import com.apu.apstay.services.AccountRegistrationService;
import com.apu.apstay.staff.manager.models.viewmodels.registration.AccountRegistrationIndexViewModel;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
@WebServlet(name = "AccountRegistrationsHomeServlet", urlPatterns = {"/manager/registrations"})
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
        
        request.setAttribute("activeNav", "registrations");
        
        var _registrations = accountRegistrationService.getAll();

        var _vm = _registrations.stream()
            .map(dto -> new AccountRegistrationIndexViewModel(
                dto.id(),
                dto.name(),
                dto.createdAt(),
                dto.status()
            ))
            .collect(Collectors.toList());
        
        request.setAttribute("registrations", _vm);
        request.setAttribute("ApprovalStatus", ApprovalStatus.class);
        request.getRequestDispatcher("/WEB-INF/views/manager/registrations/index.jsp")
               .forward(request, response);
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
