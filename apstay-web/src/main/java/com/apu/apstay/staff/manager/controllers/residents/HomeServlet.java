package com.apu.apstay.staff.manager.controllers.residents;

import com.apu.apstay.services.ResidentService;
import com.apu.apstay.services.UnitService;
import com.apu.apstay.staff.manager.models.viewmodels.resident.ResidentIndexViewModel;
import com.apu.apstay.staff.manager.models.viewmodels.unit.UnitViewModel;
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
@WebServlet(name = "ResidentsHomeServlet", urlPatterns = {"/manager/residents"})
public class HomeServlet extends HttpServlet {

    @EJB
    private ResidentService residentService;
    
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
        
        request.setAttribute("activeNav", "residents");
        
        var residents = residentService.getAll();
        var availableUnits = unitService.getAvailableUnits();
        var availableUnitsVM = UnitViewModel.from(availableUnits);

        var vm = residents.stream()
                .map(dto -> new ResidentIndexViewModel(
                        dto.id(),
                        availableUnitsVM,
                        dto.fullName(),
                        dto.unitName(),
                        dto.floorNumber(),
                        dto.active()
                )).collect(Collectors.toList());
        
        request.setAttribute("residents", vm);
        request.getRequestDispatcher("/WEB-INF/views/manager/residents/index.jsp")
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
