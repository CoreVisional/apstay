package com.apu.apstay.staff.manager.controllers.staffs;

import com.apu.apstay.services.SecurityService;
import com.apu.apstay.services.UserProfileService;
import com.apu.apstay.services.UserService;
import com.apu.apstay.staff.manager.models.viewmodels.staff.SecurityDetailsViewModel;
import com.apu.apstay.staff.manager.models.viewmodels.staff.StaffDetailsViewModel;
import jakarta.ejb.EJB;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alexc
 */
@WebServlet(name = "StaffDetailsServlet", urlPatterns = {"/manager/staffs/details/*"})
public class DetailsServlet extends HttpServlet {

    @EJB
    private UserService userService;
    
    @EJB
    private UserProfileService userProfileService;
    
    @EJB
    private SecurityService securityService;

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
        if (_pathInfo == null || _pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing staff id");
            return;
        }

        try {
            var staffId = Long.parseLong(_pathInfo.substring(1));

            var user = userService.getById(staffId);
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/manager/staffs");
                return;
            }

            var profile = userProfileService.getByUserId(staffId);
            if (profile == null) {
                response.sendRedirect(request.getContextPath() + "/manager/staffs");
                return;
            }

            boolean isSecurity = user.roles().stream()
                    .anyMatch(role -> role.name().equalsIgnoreCase("security"));

            var roleName = user.roles().stream()
                    .filter(role -> 
                        role.name().equalsIgnoreCase("security") || 
                        role.name().equalsIgnoreCase("manager") || 
                        role.name().equalsIgnoreCase("superuser"))
                    .findFirst()
                    .map(role -> role.name())
                    .orElse("");

            var staffDetails = new StaffDetailsViewModel(
                    user.id(),
                    profile.name(),
                    profile.identityNumber(),
                    user.email(),
                    roleName,
                    profile.gender(),
                    profile.phone(),
                    profile.address()
            );

            if (isSecurity) {
                var securityInfo = securityService.getByUserId(staffId);
                if (securityInfo != null) {
                    staffDetails = new SecurityDetailsViewModel(
                            user.id(),
                            profile.name(),
                            profile.identityNumber(),
                            user.email(),
                            roleName,
                            profile.gender(),
                            profile.phone(),
                            profile.address(),
                            securityInfo.commuteMode()
                    );
                    
                    request.setAttribute("isSecurityStaff", true);
                }
            }

            request.setAttribute("staff", staffDetails);
            request.getRequestDispatcher("/WEB-INF/views/manager/staffs/details.jsp").forward(request, response);

        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid staff ID");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Staff Details Servlet";
    }
    // </editor-fold>
}
