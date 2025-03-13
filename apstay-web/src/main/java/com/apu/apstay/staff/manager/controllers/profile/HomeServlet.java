package com.apu.apstay.staff.manager.controllers.profile;

import com.apu.apstay.auth.models.viewmodels.SessionUserViewModel;
import com.apu.apstay.common.models.inputmodels.UserAccountEditInputModel;
import com.apu.apstay.common.models.inputmodels.UserProfileEditInputModel;
import com.apu.apstay.common.models.viewmodels.UserAccountViewModel;
import com.apu.apstay.common.models.viewmodels.UserProfileViewModel;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.services.UserProfileService;
import com.apu.apstay.services.UserService;
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
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.cdi.HibernateValidator;

/**
 *
 * @author alexc
 */
@WebServlet(name = "ManagerProfileServlet", urlPatterns = {"/manager/profile"})
public class HomeServlet extends HttpServlet {

    @Inject
    @HibernateValidator
    private Validator validator;
    
    @EJB
    private UserService userService;
    
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

        var sessionUser = (SessionUserViewModel) request.getSession().getAttribute("user");

        var userProfile = userProfileService.getByUserId(sessionUser.getId());

        var accountVM = new UserAccountViewModel(
                sessionUser.getUsername(),
                sessionUser.getEmail()
        );
        
        var profileVM = new UserProfileViewModel(
                userProfile.name(),
                userProfile.identityNumber(),
                userProfile.gender(),
                userProfile.phone(),
                userProfile.address()
        );
        
        request.setAttribute("accountVM", accountVM);
        request.setAttribute("profileVM", profileVM);
        
        request.getRequestDispatcher("/WEB-INF/views/manager/profile/index.jsp").forward(request, response);
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
        
        var sessionUser = (SessionUserViewModel) request.getSession().getAttribute("user");
        var userId = sessionUser.getId();
        var formType = request.getParameter("formType");
        var processedParams = RequestParameterProcessor.getProcessedParameters(request);
        
        try {
            if ("account".equals(formType)) {
                handleAccountUpdate(request, response, userId, processedParams);
            } else if ("personal".equals(formType)) {
                handlePersonalUpdate(request, response, userId, processedParams);
            }
        } catch (BusinessRulesException e) {
            setErrorMessage(request, e.getMessage());
            response.sendRedirect(request.getContextPath() + "/manager/profile");
        } catch (IllegalAccessException | InvocationTargetException e) {
            setErrorMessage(request, "An error occurred while updating your profile");
            response.sendRedirect(request.getContextPath() + "/manager/profile");
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
    
    private void handleAccountUpdate(HttpServletRequest request, HttpServletResponse response, 
            Long userId, Map<String, String[]> processedParams) 
            throws ServletException, IOException, IllegalAccessException, InvocationTargetException, BusinessRulesException {
        
        var accountInput = new UserAccountEditInputModel();
        BeanUtils.populate(accountInput, processedParams);
        
        var violations = validator.validate(accountInput);
        if (!violations.isEmpty()) {
            Map<String, String> errors = extractValidationErrors(violations);

            var accountVM = new UserAccountViewModel(
                    accountInput.getUsername(),
                    accountInput.getEmail()
            );

            var userProfile = userProfileService.getByUserId(userId);
            
            var profileVM = new UserProfileViewModel(
                    userProfile.name(),
                    userProfile.identityNumber(),
                    userProfile.gender(),
                    userProfile.phone(),
                    userProfile.address()
            );

            request.setAttribute("accountVM", accountVM);
            request.setAttribute("profileVM", profileVM);
            request.setAttribute("errors", errors);
            request.setAttribute("editMode", "account");
            request.getRequestDispatcher("/WEB-INF/views/manager/profile/index.jsp").forward(request, response);
            return;
        }

        userService.updateUserAccount(userId, accountInput.getUsername(), accountInput.getEmail());

        updateSessionUser(request, userId);
        
        setSuccessMessage(request, "Account information updated successfully");
        response.sendRedirect(request.getContextPath() + "/manager/profile");
    }
    
    private void handlePersonalUpdate(HttpServletRequest request, HttpServletResponse response, 
            Long userId, Map<String, String[]> processedParams) 
            throws ServletException, IOException, IllegalAccessException, InvocationTargetException, BusinessRulesException {

        var profileInput = new UserProfileEditInputModel();
        BeanUtils.populate(profileInput, processedParams);

        var violations = validator.validate(profileInput);
        if (!violations.isEmpty()) {
            Map<String, String> errors = extractValidationErrors(violations);

            var sessionUser = (SessionUserViewModel) request.getSession().getAttribute("user");
            var accountVM = new UserAccountViewModel(
                    sessionUser.getUsername(),
                    sessionUser.getEmail()
            );

            var profileVM = new UserProfileViewModel(
                    profileInput.getName(),
                    profileInput.getIdentityNumber(),
                    profileInput.getGender(),
                    profileInput.getPhone(),
                    profileInput.getAddress()
            );

            request.setAttribute("accountVM", accountVM);
            request.setAttribute("profileVM", profileVM);
            request.setAttribute("errors", errors);
            request.setAttribute("editMode", "personal");
            request.getRequestDispatcher("/WEB-INF/views/manager/profile/index.jsp").forward(request, response);
            return;
        }

        userProfileService.updateProfile(
                userId, 
                profileInput.getName(), 
                profileInput.getIdentityNumber(), 
                profileInput.getGender(), 
                profileInput.getPhone(), 
                profileInput.getAddress()
        );

        updateSessionUser(request, userId);

        setSuccessMessage(request, "Personal information updated successfully");
        response.sendRedirect(request.getContextPath() + "/manager/profile");
    }
    
    private <T> Map<String, String> extractValidationErrors(Set<ConstraintViolation<T>> violations) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<T> violation : violations) {
            var propertyPath = violation.getPropertyPath().toString();
            var message = violation.getMessage();
            errors.put(propertyPath, message);
        }
        return errors;
    }
    
    private void updateSessionUser(HttpServletRequest request, Long userId) {
        var userDto = userService.getById(userId);
        var profileDto = userProfileService.getByUserId(userId);
        var sessionUser = new SessionUserViewModel(userDto, profileDto);
        request.getSession().setAttribute("user", sessionUser);
    }
    
    private void setSuccessMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("notice", message);
        request.getSession().setAttribute("noticeBg", "alert-success");
    }

    private void setErrorMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("notice", message);
        request.getSession().setAttribute("noticeBg", "alert-danger");
    }
    // </editor-fold>
}
