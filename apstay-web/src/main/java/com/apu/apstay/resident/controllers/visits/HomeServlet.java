package com.apu.apstay.resident.controllers.visits;

import com.apu.apstay.resident.models.viewmodels.visit.VisitIndexViewModel;
import com.apu.apstay.security.SessionContext;
import com.apu.apstay.services.ResidentService;
import com.apu.apstay.services.UserService;
import com.apu.apstay.services.VisitCodeService;
import com.apu.apstay.services.VisitRequestService;
import com.apu.apstay.services.VisitorService;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
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
@WebServlet(name = "VisitsHomeServlet", urlPatterns = {"/resident/visits"})
public class HomeServlet extends HttpServlet {
    
    @Inject
    private SessionContext sessionContext;
    
    @EJB
    private UserService userService;
    
    @EJB
    private ResidentService residentService;
    
    @EJB
    private VisitRequestService visitRequestService;
    
    @EJB
    private VisitorService visitorService;
    
    @EJB
    private VisitCodeService visitCodeService;
    
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
        
        request.setAttribute("activeNav", "visits");

        var _currentUserId = sessionContext.getCurrentUserId();
        var _currentUser = userService.getById(_currentUserId);
        var _residentId = residentService.getByUserId(_currentUser.id());
        if (_residentId == null) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have access to this resource.");
            return;
        }
        
        var _visitDtos = visitRequestService.getAllByResidentId(_residentId);
        
        var visits = _visitDtos.stream()
                .map(dto -> {
                    var visitorName = visitorService.getNameById(dto.visitorId());
                    var verificationCode = visitCodeService.getCodeByVisitRequestId(dto.id());
                    
                    return new VisitIndexViewModel(
                            dto.id(),
                            visitorName,
                            verificationCode,
                            dto.arrivalDateTime(),
                            dto.status(),
                            dto.active()
                    );
                })
                .collect(Collectors.toList());
                
        request.setAttribute("visits", visits);
        
        request.getRequestDispatcher("/WEB-INF/views/resident/e-visitor/index.jsp")
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
