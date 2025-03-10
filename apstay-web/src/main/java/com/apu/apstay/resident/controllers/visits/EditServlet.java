package com.apu.apstay.resident.controllers.visits;

import com.apu.apstay.resident.models.inputmodels.VisitRequestEditInputModel;
import com.apu.apstay.resident.models.viewmodels.visit.VisitEditViewModel;
import com.apu.apstay.services.VisitCodeService;
import com.apu.apstay.services.VisitRequestService;
import com.apu.apstay.services.VisitorService;
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
@WebServlet(name = "VisitEditServlet", urlPatterns = {"/resident/visits/edit/*"})
public class EditServlet extends HttpServlet {

    @Inject
    @HibernateValidator
    private Validator validator;
    
    @EJB
    private VisitRequestService visitRequestService;
    
    @EJB
    private VisitorService visitorService;
    
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
        
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing visit request id");
            return;
        }
        
        try {
            var _visitRequestId = Long.valueOf(pathInfo.substring(1));
            var _visit = visitRequestService.getById(_visitRequestId);
            
            if (_visit == null) {
                request.setAttribute("errorMessage", "Visit request not found");
                request.getRequestDispatcher("/WEB-INF/views/resident/visits/index.jsp").forward(request, response);
                return;
            }

            var _visitorName = visitorService.getNameById(_visit.visitorId());
            
            var verificationCode = visitCodeService.getCodeByVisitRequestId(_visit.id());

            var _vm = new VisitEditViewModel(
                    _visit.id(),
                    _visitorName,
                    verificationCode,
                    _visit.arrivalDateTime(),
                    _visit.status()
            );

            request.setAttribute("visit", _vm);
            request.getRequestDispatcher("/WEB-INF/views/resident/e-visitor/edit.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/resident/visits");
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
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing visit request id");
            return;
        }
        
        try {
    
            var _vrId = Long.valueOf(pathInfo.substring(1));
            var _input = new VisitRequestEditInputModel();
            
            Map<String, String[]> processedParams = RequestParameterProcessor.getProcessedParameters(request);
            BeanUtils.populate(_input, processedParams);

            var violations = validator.validate(_input);
            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<VisitRequestEditInputModel> violation : violations) {
                    String propertyPath = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    errors.put(propertyPath, message);
                }
                request.setAttribute("errors", errors);
                doGet(request, response);
                return;
            }

            var _updatedVisit = visitRequestService.changeStatus(_vrId, _input.getStatus());
            
            if (_updatedVisit == null) {
                request.setAttribute("notice", "Visit request not found");
                request.setAttribute("noticeBg", "alert-danger");
                doGet(request, response);
                return;
            }
            
            var _statusMessage = _input.getStatus().toString().toLowerCase();
            request.getSession().setAttribute("notice", "Visit request has been " + _statusMessage + " successfully");
            request.getSession().setAttribute("noticeBg", "alert-success");
            response.sendRedirect(request.getContextPath() + "/resident/visits");
        } catch (IllegalAccessException | InvocationTargetException e) {
            request.setAttribute("notice", "Something went wrong while updating the status of this visit.");
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
    // </editor-fold>
}
