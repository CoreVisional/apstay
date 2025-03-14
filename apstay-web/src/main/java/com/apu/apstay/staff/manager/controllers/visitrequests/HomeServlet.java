package com.apu.apstay.staff.manager.controllers.visitrequests;

import com.apu.apstay.services.VisitRequestService;
import com.apu.apstay.staff.manager.models.viewmodels.visitrequest.VisitRequestIndexViewModel;
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
@WebServlet(name = "VisitRequestsHomeServlet", urlPatterns = {"/manager/visit-logs"})
public class HomeServlet extends HttpServlet {

    @EJB
    private VisitRequestService visitRequestService;
    
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

        request.setAttribute("activeNav", "visitrequests");

        var visitRequests = visitRequestService.getAllWithDetails();

        var vm = visitRequests.stream()
                .map(vr -> new VisitRequestIndexViewModel(
                    vr.getVisitor().getName(),
                    vr.getResident().getUser().getUserProfile().getName(),
                    vr.getResident().getUnit().getUnitName(),
                    vr.getArrivalDateTime(),
                    vr.getStatus()
                ))
                .collect(Collectors.toList());

        request.setAttribute("visitrequests", vm);
        request.getRequestDispatcher("/WEB-INF/views/manager/visitrequests/index.jsp")
               .forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Visit Logs Home Servlet";
    }
    // </editor-fold>
}
