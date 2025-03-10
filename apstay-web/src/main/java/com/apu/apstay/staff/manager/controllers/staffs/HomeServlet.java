package com.apu.apstay.staff.manager.controllers.staffs;

import com.apu.apstay.dtos.UserDto;
import com.apu.apstay.dtos.UserProfileDto;
import com.apu.apstay.services.UserService;
import com.apu.apstay.services.UserProfileService;
import com.apu.apstay.security.SessionContext;
import com.apu.apstay.staff.manager.models.viewmodels.staff.StaffIndexViewModel;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
@WebServlet(name = "StaffsHomeServer", urlPatterns = {"/manager/staffs"})
public class HomeServlet extends HttpServlet {

    @Inject
    private SessionContext sessionContext;
    
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
        request.setAttribute("activeNav", "staffs");

        var currentUserId = sessionContext.getCurrentUserId();
        var currentUser = (UserDto) null;
        if (currentUserId != null) {
            currentUser = userService.getById(currentUserId);
        }

        boolean isSuperuser = false;
        if (currentUser != null) {
            isSuperuser = currentUser.roles().stream()
                .anyMatch(role -> role.name().equalsIgnoreCase("superuser"));
        }

        var userDtos = userService.getStaffUsers(isSuperuser);

        var userIds = userDtos.stream()
                .map(UserDto::id)
                .collect(Collectors.toList());
        List<UserProfileDto> profileDtos = userProfileService.getByUserIds(userIds);

        var _vm = StaffIndexViewModel.from(userDtos, profileDtos);

        request.setAttribute("staffs", _vm);
        request.getRequestDispatcher("/WEB-INF/views/manager/staffs/index.jsp")
               .forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Staffs Home Servlet";
    }
    // </editor-fold>
}
