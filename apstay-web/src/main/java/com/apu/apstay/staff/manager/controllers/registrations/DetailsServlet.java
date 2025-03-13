package com.apu.apstay.staff.manager.controllers.registrations;

import com.apu.apstay.services.AccountRegistrationService;
import com.apu.apstay.staff.manager.models.viewmodels.registration.AccountRegistrationDetailsViewModel;
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
@WebServlet(name = "AccountRegistrationsDetailsServlet", urlPatterns = {"/manager/registrations/details/*"})
public class DetailsServlet extends HttpServlet {

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
        
        var _pathInfo = request.getPathInfo();
        if (_pathInfo == null || _pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing registration id");
            return;
        }
        
        try {
            var _registrationId = Long.parseLong(_pathInfo.substring(1));
            var _dto = accountRegistrationService.getById(_registrationId);

            var _vm = new AccountRegistrationDetailsViewModel(
                    _dto.id(),
                    _dto.unitId(),
                    _dto.name(),
                    _dto.reviewerName(),
                    _dto.identityNumber(),
                    _dto.email(),
                    _dto.gender(),
                    _dto.phone(),
                    _dto.address(),
                    _dto.unitName(),
                    _dto.status(),
                    _dto.remarks(),
                    _dto.createdAt()
            );
            
            request.setAttribute("registration", _vm);
            request.getRequestDispatcher("/WEB-INF/views/manager/registrations/details.jsp")
                   .forward(request, response);
            
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid registration ID");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Account Registration Details Servlet";
    }
    // </editor-fold>
}
