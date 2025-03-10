package com.apu.apstay.staff.manager.controllers.staffs;

import com.apu.apstay.auth.models.viewmodels.SessionUserViewModel;
import com.apu.apstay.commands.users.StaffSaveCommand;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.utils.RequestParameterProcessor;
import com.apu.apstay.services.RoleService;
import com.apu.apstay.services.UserProfileService;
import com.apu.apstay.services.UserService;
import com.apu.apstay.staff.manager.models.inputmodels.StaffInputModel;
import com.apu.apstay.staff.manager.models.viewmodels.staff.RoleDropdownItem;
import com.apu.apstay.staff.manager.models.viewmodels.staff.StaffEditViewModel;
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
@WebServlet(name = "StaffEditServlet", urlPatterns = {"/manager/staffs/edit/*"})
public class EditServlet extends HttpServlet {
    
    @Inject
    @HibernateValidator
    private Validator validator;
    
    @EJB
    private UserService userService;
    
    @EJB
    private RoleService roleService;
    
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
        request.setAttribute("activeNav", "staffs");
        var _pathInfo = request.getPathInfo();
        if(_pathInfo == null || _pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing staff id");
            return;
        }
        
        try {         
            var _staffId = Long.valueOf(_pathInfo.substring(1));
            var _staff = StaffEditViewModel.from(
                userService.getById(_staffId),
                userProfileService.getByUserId(_staffId)
            );
            
            if (_staff == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Staff not found");
                return;
            }
            
            var _sessionUser = (SessionUserViewModel) request.getSession().getAttribute("user");
            boolean _isSuperuser = _sessionUser != null && _sessionUser.hasRole("superuser");
            var _roles = RoleDropdownItem.fromList(roleService.getAssignableRoles(_isSuperuser));
            
            request.setAttribute("staff", _staff);
            request.setAttribute("roles", _roles);
            request.getRequestDispatcher("/WEB-INF/views/manager/staffs/edit.jsp")
                   .forward(request, response);
            
        } catch(NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid staff id");
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
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing staff id");
            return;
        }
        
        try {
            var _staffId = Long.valueOf(_pathInfo.substring(1));
            var _input = new StaffInputModel();
            
            Map<String, String[]> _processedParams = RequestParameterProcessor.getProcessedParameters(request);
            BeanUtils.populate(_input, _processedParams);
            
            var _violations = validator.validate(_input);
            if (!_violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<StaffInputModel> violation : _violations) {
                    String propertyPath = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.setAttribute("errors", errors);
                doGet(request, response);
                return;
            }
            
            var _command = mapToCommand(_input);
            
            userService.updateStaff(_staffId, _command);
            
            request.getSession().setAttribute("notice", "Staff Updated Successfully");
            request.getSession().setAttribute("noticeBg", "alert-success");
            response.sendRedirect(request.getContextPath() + "/manager/staffs");
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
    
    private StaffSaveCommand mapToCommand(StaffInputModel input) {
        var _roleId = input.getRoleId();
        return new StaffSaveCommand(
                _roleId,
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
