package com.apu.apstay.staff.manager.controllers.residents;

import com.apu.apstay.commands.users.ResidentUpdateCommand;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.services.ResidentService;
import com.apu.apstay.services.UnitService;
import com.apu.apstay.services.UserProfileService;
import com.apu.apstay.services.UserService;
import com.apu.apstay.staff.manager.models.inputmodels.ResidentEditInputModel;
import com.apu.apstay.staff.manager.models.viewmodels.resident.ResidentEditViewModel;
import com.apu.apstay.staff.manager.models.viewmodels.unit.UnitViewModel;
import com.apu.apstay.utils.RequestParameterProcessor;
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
@WebServlet(name = "ResidentEditServlet", urlPatterns = {"/manager/residents/edit/*"})
public class EditServlet extends HttpServlet {

    @Inject
    @HibernateValidator
    private Validator validator;
    
    @EJB
    private UserService userService;
    
    @EJB
    private UserProfileService userProfileService;
    
    @EJB
    private UnitService unitService;
    
    @EJB
    private ResidentService residentService;
    
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
        
        var _pathInfo = request.getPathInfo();
        if(_pathInfo == null || _pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing resident id");
            return;
        }
        
        try {
            var _residentId = Long.valueOf(_pathInfo.substring(1));
            var resident = residentService.getById(_residentId);
            
            if (resident == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resident not found");
                return;
            }

            var user = userService.getById(resident.getUser().getId());
            var userProfile = userProfileService.getByUserId(resident.getUser().getId());
            
            if (user == null || userProfile == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resident user data not found");
                return;
            }

            var currentUnit = resident.getUnit();
            var availableUnits = unitService.getAvailableUnits();

            if (currentUnit != null) {
                boolean currentUnitIncluded = availableUnits.stream()
                    .anyMatch(u -> u.id().equals(currentUnit.getId()));
                
                if (!currentUnitIncluded) {
                    availableUnits.add(unitService.getById(currentUnit.getId()));
                }
            }
            
            var availableUnitsVM = UnitViewModel.from(availableUnits);

            var vm = new ResidentEditViewModel(
                    resident.getId(),
                    currentUnit != null ? currentUnit.getId() : null,
                    currentUnit != null ? currentUnit.getUnitName() : null,
                    currentUnit != null ? currentUnit.getFloorNumber() : null,
                    availableUnitsVM,
                    user.username(),
                    user.email(),
                    userProfile.name(),
                    userProfile.identityNumber(),
                    userProfile.gender(),
                    userProfile.phone(),
                    userProfile.address()
            );
            
            request.setAttribute("resident", vm);
            request.getRequestDispatcher("/WEB-INF/views/manager/residents/edit.jsp")
                   .forward(request, response);
            
        } catch(NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid resident id");
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
        
        var _pathInfo = request.getPathInfo();
        if(_pathInfo == null || _pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing resident id");
            return;
        }
        
        try {
            var residentId = Long.valueOf(_pathInfo.substring(1));
            
            var input = new ResidentEditInputModel();
            
            Map<String, String[]> _processedParams = RequestParameterProcessor.getProcessedParameters(request);
            BeanUtils.populate(input, _processedParams);
            
            var _violations = validator.validate(input);
            if (!_violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<ResidentEditInputModel> violation : _violations) {
                    var propertyPath = violation.getPropertyPath().toString();
                    var message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.setAttribute("errors", errors);
                doGet(request, response);
                return;
            }
            
            var command = mapToCommand(input);
            
            residentService.updateResident(residentId, command);
            
            request.getSession().setAttribute("notice", "Resident Updated Successfully");
            request.getSession().setAttribute("noticeBg", "alert-success");
            response.sendRedirect(request.getContextPath() + "/manager/residents");
        } catch (BusinessRulesException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getErrorKey(), e.getMessage());
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/views/manager/residents/edit.jsp").forward(request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            request.setAttribute("notice", "Something went wrong while updating this resident");
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
    
    private ResidentUpdateCommand mapToCommand(ResidentEditInputModel input) {
        return new ResidentUpdateCommand(
                input.getUnitId(),
                input.getUsername(),
                input.getEmail(),
                input.getFullName(),
                input.getIdentityNumber(),
                input.getGender(),
                input.getPhone(),
                input.getAddress()
        );
    }
    // </editor-fold>
}
