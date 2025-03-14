package com.apu.apstay.staff.manager.controllers.reports;

import com.apu.apstay.services.AccountRegistrationService;
import com.apu.apstay.staff.manager.models.viewmodels.reports.ApprovalEfficiencyReportViewModel;
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
@WebServlet(name = "ApprovalEfficiencyServlet", urlPatterns = {"/manager/reports/approval-efficiency"})
public class ApprovalEfficiencyServlet extends HttpServlet {

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

        request.setAttribute("activeNav", "reports");
 
        var reportData = accountRegistrationService.getApprovalEfficiencyReportData();

        var vm = new ApprovalEfficiencyReportViewModel(
            (double) reportData.get("averageApprovalTime"),
            (double) reportData.get("approvalRate"),
            (int) reportData.get("pendingCount"),
            (List<Integer>) reportData.get("statusDistribution"),
            (List<Map<String, Object>>) reportData.get("recentRegistrations")
        );

        request.setAttribute("reportData", vm);
        request.getRequestDispatcher("/WEB-INF/views/manager/reports/approval-efficiency.jsp")
               .forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Registration Approval Efficiency Report";
    }
    // </editor-fold>
}
