package com.apu.apstay.auth.controllers;

import com.apu.apstay.commands.auth.RegistrationCreateCommand;
import com.apu.apstay.utils.RequestParameterProcessor;
import com.apu.apstay.auth.models.inputmodels.RegistrationInputModel;
import com.apu.apstay.commands.users.UserCreateCommand;
import com.apu.apstay.services.AccountRegistrationService;
import com.apu.apstay.services.UnitService;
import com.apu.apstay.services.UserService;
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
@WebServlet(name = "RegistrationServlet", urlPatterns = {"/register"})
public class RegistrationServlet extends HttpServlet {

    @Inject
    @HibernateValidator
    private Validator validator;
    
    @EJB
    private AccountRegistrationService accountRegistrationService;

    @EJB
    private UserService userService;
    
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
        
        var availableUnits = unitService.getAvailableUnits();
        
        var vm = UnitViewModel.from(availableUnits);
        
        request.setAttribute("availableUnits", vm);
        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
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
        
        var registrationInput = new RegistrationInputModel();
        var processedParams = RequestParameterProcessor.getProcessedParameters(request);

        try {
            BeanUtils.populate(registrationInput, processedParams);

            var violations = validator.validate(registrationInput);
            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<RegistrationInputModel> violation : violations) {
                    var propertyPath = violation.getPropertyPath().toString();
                    var message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }

            var _userCommand = mapToUserCreateCommand(registrationInput);
            var _user = userService.createUser(_userCommand);

            var accountCommand = mapToRegistrationCreateCommand(registrationInput, _user.getId());
            accountRegistrationService.createAccountRegistration(accountCommand);

            request.getSession().setAttribute("notice", "Registration request submitted successfully. Please wait for approval.");
            request.getSession().setAttribute("noticeBg", "alert-success");
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);

        } catch (IllegalAccessException | InvocationTargetException e) {
            request.setAttribute("notice", "An error occurred during registration.");
            request.setAttribute("noticeBg", "alert-danger");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Registration Servlet";
    }
    
    private RegistrationCreateCommand mapToRegistrationCreateCommand(RegistrationInputModel input, Long userId) {
        return new RegistrationCreateCommand(
                userId,
                input.getUnitId(),
                input.getEmail(),
                input.getFullName(),
                input.getIdentityNumber(),
                input.getPhone(),
                input.getGender(),
                input.getAddress()
        );
    }
    
    private UserCreateCommand mapToUserCreateCommand(RegistrationInputModel input) {
        return new UserCreateCommand(
                input.getUsername(),
                input.getEmail(),
                input.getPassword()
        );
    }
    // </editor-fold>
}
