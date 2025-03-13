package com.apu.apstay.staff.manager.controllers.reports;

import com.apu.apstay.services.UnitService;
import com.apu.apstay.staff.manager.models.viewmodels.reports.UnitOccupancyReportViewModel;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * @author alexc
 */
@WebServlet(name = "UnitOccupancyServlet", urlPatterns = {"/manager/reports/unit-occupancy"})
public class UnitOccupancyServlet extends HttpServlet {

    @EJB
    private UnitService unitService;
    
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

        Map<String, Object> reportData = unitService.getUnitOccupancyReportData();

        var vm = new UnitOccupancyReportViewModel(
            (int) reportData.get("totalUnits"),
            (int) reportData.get("activeUnits"),
            (int) reportData.get("inactiveUnits"),
            (int) reportData.get("fullyOccupiedUnits"),
            (int) reportData.get("vacantUnits"),
            (Map<String, Object>) reportData.get("occupancyChartData"),
            (Map<String, Object>) reportData.get("floorOccupancyData"),
            (Map<String, Object>) reportData.get("capacityDistributionData")
        );

        request.setAttribute("reportData", vm);
        request.setAttribute("activeNav", "reports");
        request.getRequestDispatcher("/WEB-INF/views/manager/reports/unit-occupancy.jsp")
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
