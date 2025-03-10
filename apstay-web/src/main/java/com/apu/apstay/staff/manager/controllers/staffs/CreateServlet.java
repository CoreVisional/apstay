package com.apu.apstay.staff.manager.controllers.staffs;

import com.apu.apstay.auth.models.viewmodels.SessionUserViewModel;
import com.apu.apstay.commands.users.StaffSaveCommand;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.utils.RequestParameterProcessor;
import com.apu.apstay.services.RoleService;
import com.apu.apstay.services.UserService;
import com.apu.apstay.staff.manager.models.inputmodels.StaffInputModel;
import com.apu.apstay.staff.manager.models.viewmodels.staff.RoleDropdownItem;
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
@WebServlet(name = "StaffCreateServlet", urlPatterns = {"/manager/staffs/create"})
public class CreateServlet extends HttpServlet {

    @Inject
    @HibernateValidator
    private Validator validator;
    
    @EJB
    private UserService userService;
    
    @EJB
    private RoleService roleService;
    
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

        var sessionUser = (SessionUserViewModel) request.getSession().getAttribute("user");
        boolean isSuperuser = sessionUser != null && sessionUser.hasRole("superuser");

        var roles = RoleDropdownItem.fromList(roleService.getAssignableRoles(isSuperuser));
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("/WEB-INF/views/manager/staffs/create.jsp").forward(request, response);
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
        
        var _input = new StaffInputModel();
        
        Map<String, String[]> _processedParams = RequestParameterProcessor.getProcessedParameters(request);
        
        try {
            BeanUtils.populate(_input, _processedParams);
            
            Set<ConstraintViolation<StaffInputModel>> violations = validator.validate(_input);
            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<StaffInputModel> violation : violations) {
                    String propertyPath = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/WEB-INF/views/manager/staffs/create.jsp").forward(request, response);
                return;
            }
            
            var _command = mapToCommand(_input);
            userService.createStaff(_command);
            
            request.getSession().setAttribute("notice", "Staff created successfully!");
            request.getSession().setAttribute("noticeBg", "alert-success");
            response.sendRedirect(request.getContextPath() + "/manager/staffs"); 
        } catch (BusinessRulesException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getErrorKey(), e.getMessage());

            request.setAttribute("errors", errors);
            var roles = RoleDropdownItem.fromList(roleService.getStaffRoles());
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/WEB-INF/views/manager/staffs/create.jsp").forward(request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            request.setAttribute("notice", "Some thing went wrong.");
            request.setAttribute("noticeBg", "alert-danger");
            request.getRequestDispatcher("/WEB-INF/views/manager/staffs/create.jsp").forward(request, response);
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
