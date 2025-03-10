package com.apu.apstay.staff.manager.controllers.registrations;

import com.apu.apstay.security.SessionContext;
import com.apu.apstay.services.AccountRegistrationService;
import com.apu.apstay.staff.manager.models.inputmodels.RegistrationRejectInputModel;
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
@WebServlet(name = "RejectionServlet", urlPatterns = {"/manager/registrations/reject"})
public class RejectionServlet extends HttpServlet {

    @Inject
    @HibernateValidator
    private Validator validator;
    
    @Inject
    private SessionContext sessionContext;
    
    @EJB
    private AccountRegistrationService accountRegistrationService;
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        
        var _input = new RegistrationRejectInputModel();
        var _processedParams = RequestParameterProcessor.getProcessedParameters(request);
        
        try {
            BeanUtils.populate(_input, _processedParams);
            _input.setReviewerId(sessionContext.getCurrentUserId());
            Set<ConstraintViolation<RegistrationRejectInputModel>> violations = validator.validate(_input);
            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<RegistrationRejectInputModel> violation : violations) {
                    String propertyPath = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.getSession().setAttribute("errors", errors);
                response.sendRedirect(request.getContextPath() + "/manager/registrations/details/" + _input.getRegistrationId());
                return;
            }
            
            accountRegistrationService.reject(_input.getRegistrationId(), _input.getReviewerId() , _input.getRemarks());

            request.getSession().setAttribute("notice", "Account Request Rejected Successfully");
            request.getSession().setAttribute("noticeBg", "alert-success");
            response.sendRedirect(request.getContextPath() + "/manager/registrations");
        } catch (IllegalAccessException | InvocationTargetException e) {
            request.setAttribute("notice", "Some thing went wrong while attempting to approve registration.");
            request.setAttribute("noticeBg", "alert-danger");
            request.getRequestDispatcher("/WEB-INF/views/manager/registrations/details.jsp").forward(request, response);
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
    // </editor-fold>
}
