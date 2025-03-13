package com.apu.apstay.staff.manager.controllers.reports;

import com.apu.apstay.services.UserProfileService;
import com.apu.apstay.staff.manager.models.viewmodels.reports.GenderDistributionReportViewModel;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexc
 */
@WebServlet(name = "GenderDistributionServlet", urlPatterns = {"/manager/reports/gender-distribution"})
public class GenderDistributionServlet extends HttpServlet {

    @EJB
    private UserProfileService userProfileService;
    
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
        
        Map<String, Object> data = userProfileService.getGenderDistribution();

        Map<String, Integer> genderCounts = (Map<String, Integer>) data.get("genderCounts");
        int totalCount = (int) data.get("totalCount");

        Map<String, Double> genderPercentages = new HashMap<>();
        for (Map.Entry<String, Integer> entry : genderCounts.entrySet()) {
            double percentage = totalCount > 0 
                ? (double) entry.getValue() / totalCount * 100.0 
                : 0.0;
            genderPercentages.put(entry.getKey(), percentage);
        }

        var vm = new GenderDistributionReportViewModel(
                genderCounts,
                genderPercentages,
                totalCount
        );

        request.setAttribute("genderData", vm);
        request.getRequestDispatcher("/WEB-INF/views/manager/reports/gender-distribution.jsp")
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
