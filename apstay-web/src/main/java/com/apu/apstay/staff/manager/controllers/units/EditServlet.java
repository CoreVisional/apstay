package com.apu.apstay.staff.manager.controllers.units;

import com.apu.apstay.commands.units.UnitUpdateCommand;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.utils.RequestParameterProcessor;
import com.apu.apstay.services.UnitService;
import com.apu.apstay.staff.manager.models.inputmodels.UnitEditInputModel;
import com.apu.apstay.staff.manager.models.viewmodels.unit.UnitViewModel;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.cdi.HibernateValidator;

/**
 *
 * @author alexc
 */
@WebServlet(name = "UnitEditServlet", urlPatterns = {"/manager/units/edit/*"})
public class EditServlet extends HttpServlet {

    @Inject
    @HibernateValidator
    private Validator validator;
    
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
        
        request.setAttribute("activeNav", "units");
        
        var pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing unit id");
            return;
        }
        
        try {
            var unitId = Long.valueOf(pathInfo.substring(1));
            var unit = UnitViewModel.from(unitService.getById(unitId));
            
            if (unit == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unit not found");
                return;
            }

            request.setAttribute("unit", unit);
            request.getRequestDispatcher("/WEB-INF/views/manager/units/edit.jsp")
                   .forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid unit id");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        var pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing unit id");
            return;
        }
        
        try {
            var unitId = Long.valueOf(pathInfo.substring(1));
            var unitInput = new UnitEditInputModel();

            Map<String, String[]> processedParams = RequestParameterProcessor.getProcessedParameters(request);
            BeanUtils.populate(unitInput, processedParams);

            var violations = validator.validate(unitInput);
            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<UnitEditInputModel> violation : violations) {
                    String propertyPath = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.setAttribute("errors", errors);
                doGet(request, response);
                return;
            }

            var command = mapToCommand(unitInput);
            
            unitService.updateUnit(unitId, command);
            
            request.getSession().setAttribute("notice", "Unit Updated Successfully");
            request.getSession().setAttribute("noticeBg", "alert-success");
            response.sendRedirect(request.getContextPath() + "/manager/units");
        } catch (BusinessRulesException e) {
            request.setAttribute("error", e.getMessage());
            doGet(request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            request.setAttribute("notice", "Something went wrong while updating this unit.");
            request.setAttribute("noticeBg", "alert-danger");
            doGet(request, response);
        }
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
    
    private UnitUpdateCommand mapToCommand(UnitEditInputModel input) {
        return new UnitUpdateCommand(
                input.getUnitName(),
                input.getFloorNumber(),
                input.getCapacity(),
                input.isActive()
        );
    }
    // </editor-fold>
}
