package com.apu.apstay.staff.security.controllers.home;

import com.apu.apstay.enums.VisitRequestStatus;
import com.apu.apstay.services.VisitCodeService;
import com.apu.apstay.services.VisitRequestService;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author alexc
 */
@WebServlet(name = "SecurityHomeServlet", urlPatterns = {"/security"})
public class HomeServlet extends HttpServlet {

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
        
        request.setAttribute("activeNav", "home");
        
        request.getRequestDispatcher("/WEB-INF/views/security/home/index.jsp").forward(request, response);
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
        
        var verificationCode = request.getParameter("verificationCode");

        if (!visitCodeService.isCodeValid(verificationCode)) {
            var errorMessage = visitCodeService.getVerificationMessage(verificationCode);

            request.getSession().setAttribute("errorModal", true);
            request.getSession().setAttribute("errorMessage", errorMessage);

            response.sendRedirect(request.getContextPath() + "/security");
            return;
        }

        var visitRequestId = visitCodeService.getByCode(verificationCode);

        visitRequestService.changeStatus(visitRequestId, VisitRequestStatus.REACHED);

        visitCodeService.deactivateCode(visitRequestId);

        request.getSession().setAttribute("notice", "Verified!");
        request.getSession().setAttribute("noticeBg", "alert-success");

        response.sendRedirect(request.getContextPath() + "/security");
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
