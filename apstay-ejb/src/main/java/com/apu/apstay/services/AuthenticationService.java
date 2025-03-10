package com.apu.apstay.services;

import com.apu.apstay.dtos.UserDto;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.utils.PasswordUtil;
import com.apu.apstay.security.SessionContext;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 *
 * @author alexc
 */
@Stateless
public class AuthenticationService {

    @EJB
    private UserService userManager;

    @Inject
    private SessionContext sessionContext;

    // <editor-fold defaultstate="collapsed" desc="Implementations of AuthenticationService">
    public boolean login(String loginKey, String password) throws BusinessRulesException {
        var _userDto = userManager.getByLoginKey(loginKey);
        if (_userDto == null) {
            return false;
        }
        if (!validateBusinessRules(_userDto, password)) {
            return false;
        }
        sessionContext.setCurrentUserId(_userDto.id());
        userManager.updateLoginSuccess(_userDto.id());
        return true;
    }

    public String determineRedirectPath(String loginKey) {
        var _userDto = userManager.getByLoginKey(loginKey);
        if (_userDto == null) {
            return "/login";
        }
        boolean _isManager = hasRole(_userDto, "manager") || hasRole(_userDto, "superuser");
        boolean _isSecurityStaff = hasRole(_userDto, "security");

        if (_isManager) {
            return "/manager";
        } else if (_isSecurityStaff) {
            return "/security";
        }
        return "/resident";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Implementations">
    private boolean validateBusinessRules(UserDto userDto, String password)
            throws BusinessRulesException {
        if (!userDto.isActive()) {
            throw new BusinessRulesException("Account is not activated");
        }
        if (userDto.isLocked()) {
            throw new BusinessRulesException("Account is locked");
        }
        if (!PasswordUtil.verifyPassword(userDto.passwordHash(), password)) {
            userManager.updateLoginFailure(userDto.id());
            return false;
        }
        return true;
    }

    private boolean hasRole(UserDto userDto, String roleName) {
        return userDto.roles().stream()
                .anyMatch(role -> role.name().equalsIgnoreCase(roleName));
    }
    // </editor-fold>
}
