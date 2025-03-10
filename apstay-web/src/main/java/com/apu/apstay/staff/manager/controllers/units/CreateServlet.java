package com.apu.apstay.staff.manager.controllers.units;

import com.apu.apstay.commands.units.UnitCreateCommand;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.utils.RequestParameterProcessor;
import com.apu.apstay.services.UnitService;
import com.apu.apstay.staff.manager.models.inputmodels.UnitInputModel;
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
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.cdi.HibernateValidator;

/**
 *
 * @author alexc
 */
@WebServlet(name = "UnitCreateServlet", urlPatterns = {"/manager/units/create"})
public class CreateServlet extends HttpServlet {
    
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
        request.getRequestDispatcher("/WEB-INF/views/manager/units/create.jsp").forward(request, response);
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
        
        var _unitInput = new UnitInputModel();
        var processedParams = RequestParameterProcessor.getProcessedParameters(request);
        
        try {
            BeanUtils.populate(_unitInput, processedParams);

            Set<ConstraintViolation<UnitInputModel>> violations = validator.validate(_unitInput);
            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<UnitInputModel> violation : violations) {
                    String propertyPath = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/WEB-INF/views/manager/units/create.jsp").forward(request, response);
                return;
            }

            var _unitCommand = mapToCommand(_unitInput);
            unitService.createUnit(_unitCommand);

            request.getSession().setAttribute("notice", "New Unit Created Successfully");
            request.getSession().setAttribute("noticeBg", "alert-success");
            response.sendRedirect(request.getContextPath() + "/manager/units"); 
        } catch (BusinessRulesException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getErrorKey(), e.getMessage());
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/views/manager/units/create.jsp").forward(request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            request.setAttribute("notice", "Some thing went wrong while creating this unit.");
            request.setAttribute("noticeBg", "alert-danger");
            request.getRequestDispatcher("/WEB-INF/views/manager/units/create.jsp").forward(request, response);
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
    
    private UnitCreateCommand mapToCommand(UnitInputModel input) {
        return new UnitCreateCommand(
                input.getUnitName(),
                input.getFloorNumber()
        );
    }
    // </editor-fold>
}
