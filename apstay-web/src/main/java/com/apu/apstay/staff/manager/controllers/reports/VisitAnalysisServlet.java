package com.apu.apstay.staff.manager.controllers.reports;

import com.apu.apstay.services.VisitRequestService;
import com.apu.apstay.staff.manager.models.viewmodels.reports.VisitAnalysisReportViewModel;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexc
 */
@WebServlet(name = "VisitAnalysisServlet", urlPatterns = {"/manager/reports/visit-analysis"})
public class VisitAnalysisServlet extends HttpServlet {

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

        request.setAttribute("activeNav", "reports");

        var reportData = visitRequestService.getVisitAnalysisData();

        var vm = new VisitAnalysisReportViewModel(
            (int) reportData.get("totalRequests"),
            (int) reportData.get("reachedRequests"),
            (int) reportData.get("submittedRequests"),
            (int) reportData.get("cancelledRequests"),
            (List<Integer>) reportData.get("statusDistribution"),
            (List<String>) reportData.get("dayLabels"),
            (List<Integer>) reportData.get("dayData"),
            (List<Map<String, Object>>) reportData.get("mostVisitedUnits")
        );

        request.setAttribute("reportData", vm);
        request.getRequestDispatcher("/WEB-INF/views/manager/reports/visit-analysis.jsp")
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
