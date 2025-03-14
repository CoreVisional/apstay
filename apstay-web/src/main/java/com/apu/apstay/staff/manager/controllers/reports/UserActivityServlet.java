package com.apu.apstay.staff.manager.controllers.reports;

import com.apu.apstay.services.UserService;
import com.apu.apstay.staff.manager.models.viewmodels.reports.UserActivityReportViewModel;
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
@WebServlet(name = "UserActivityServlet", urlPatterns = {"/manager/reports/user-activity"})
public class UserActivityServlet extends HttpServlet {
    
    @EJB
    private UserService userService;
    
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
        
        var reportData = userService.getUserActivityReportData();

        var vm = new UserActivityReportViewModel(
            (int) reportData.get("totalUsers"),
            (int) reportData.get("activeUsers"),
            (int) reportData.get("lockedUsers"),
            (int) reportData.get("inactiveUsers"),
            (int) reportData.get("loginsToday"),
            (int) reportData.get("failedLoginsLastWeek"),
            (List<Integer>) reportData.get("statusDistribution"),
            (List<String>) reportData.get("dayLabels"),
            (List<Integer>) reportData.get("successfulLogins"),
            (List<Integer>) reportData.get("failedLogins"),
            (List<Map<String, Object>>) reportData.get("userActivity")
        );

        request.setAttribute("reportData", vm);
        request.getRequestDispatcher("/WEB-INF/views/manager/reports/user-activity.jsp")
               .forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "User Activity Report";
    }
    // </editor-fold>
}
