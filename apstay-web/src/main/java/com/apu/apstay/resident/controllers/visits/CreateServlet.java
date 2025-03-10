package com.apu.apstay.resident.controllers.visits;

import com.apu.apstay.commands.visits.VisitRequestCreateCommand;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.resident.models.inputmodels.VisitRequestInputModel;
import com.apu.apstay.security.SessionContext;
import com.apu.apstay.services.VisitCodeService;
import com.apu.apstay.services.VisitRequestService;
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
@WebServlet(name = "VisitCreateServlet", urlPatterns = {"/resident/visits/create"})
public class CreateServlet extends HttpServlet {
    
    @Inject
    @HibernateValidator
    private Validator validator;
    
    @Inject
    private SessionContext sessionContext;
    
    @EJB
    private VisitRequestService visitRequestService;
    
    @EJB
    private VisitCodeService visitCodeService;
    
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
        
        request.setAttribute("activeNav", "visits");
        request.getRequestDispatcher("/WEB-INF/views/resident/e-visitor/create.jsp")
               .forward(request, response);
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

        var _input = new VisitRequestInputModel();
        var processedParams = RequestParameterProcessor.getProcessedParameters(request);

        try {
            BeanUtils.populate(_input, processedParams);

            var violations = validator.validate(_input);
            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<VisitRequestInputModel> violation : violations) {
                    String propertyPath = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/WEB-INF/views/resident/e-visitor/create.jsp").forward(request, response);
                return;
            }

            var visitRequestDto = visitRequestService.create(
                mapToCommand(_input, sessionContext.getCurrentUserId()));
            
            var verificationCode = visitCodeService.getCodeByVisitRequestId(visitRequestDto.id());

            request.getSession().setAttribute("verificationCode", verificationCode);
            request.getSession().setAttribute("notice", "Visit Request Created!");
            request.getSession().setAttribute("noticeBg", "alert-success");
            response.sendRedirect(request.getContextPath() + "/resident/visits");
        } catch (BusinessRulesException e) {
            Map<String, String> errors = new HashMap<>();
            errors.put(e.getErrorKey(), e.getMessage());
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/views/resident/e-visitor/create.jsp").forward(request, response);
        } catch (IllegalAccessException | InvocationTargetException e) {
            request.setAttribute("notice", "Something went wrong while creating this request.");
            request.setAttribute("noticeBg", "alert-danger");
            request.getRequestDispatcher("/WEB-INF/views/resident/e-visitor/create.jsp").forward(request, response);
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
    
    private VisitRequestCreateCommand mapToCommand(VisitRequestInputModel input, Long residentId) {
        return new VisitRequestCreateCommand(
                residentId,
                input.getArrivalDateTime(),
                input.getName(),
                input.getIdentityNumber(),
                input.getGender(),
                input.getPhone()
        );
    }
    // </editor-fold>
}
