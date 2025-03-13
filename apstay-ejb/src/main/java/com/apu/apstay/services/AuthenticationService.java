package com.apu.apstay.services;

import com.apu.apstay.dtos.UserDto;
import com.apu.apstay.entities.User;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.facades.UserFacade;
import com.apu.apstay.utils.PasswordUtil;
import com.apu.apstay.services.common.BaseService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
@Stateless
public class AuthenticationService extends BaseService {
    
    @EJB
    private UserFactory userFactory;
    
    @EJB
    private UserFacade userFacade;

    // <editor-fold defaultstate="collapsed" desc="Implementations of AuthenticationService">
    public boolean login(String loginKey, String password) throws BusinessRulesException {
        var _user = getUserByLoginKey(loginKey);
        if (_user == null) {
            return false;
        }
        if (!validateBusinessRules(_user, password)) {
            return false;
        }
        sessionContext.setCurrentUserId(_user.id());
        updateLoginSuccess(_user.id());
        return true;
    }

    public String determineRedirectPath(String loginKey) {
        var _user = getUserByLoginKey(loginKey);
        if (_user == null) {
            return "/login";
        }
        boolean _isManager = hasRole(_user, "manager") || hasRole(_user, "superuser");
        boolean _isSecurityStaff = hasRole(_user, "security");

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
        if (!PasswordUtil.verifyPassword(password, userDto.passwordHash())) {
            updateLoginFailure(userDto.id());
            return false;
        }
        return true;
    }

    private boolean hasRole(UserDto userDto, String roleName) {
        return userDto.roles().stream()
                .anyMatch(role -> role.name().equalsIgnoreCase(roleName));
    }
    
    private UserDto getUserByLoginKey(String loginKey) {
        var _result = userFacade.findByUsernameOrEmail(loginKey);
        return _result.isEmpty() ? null : userFactory.toDto(_result.get(0));
    }
    
    private void updateLoginSuccess(Long userId) {
        var _user = userFacade.find(userId);
        updateLoginAttempt(_user, true);
    }

    private void updateLoginFailure(Long userId) {
        var _user = userFacade.find(userId);
        updateLoginAttempt(_user, false);
    }
    
    private void updateLoginAttempt(User user, boolean success) {
        if (success) {
            user.setLastLogin(LocalDateTime.now());
            user.setFailedLoginAttempts(0);
        } else {
            user.setLastFailedLogin(LocalDateTime.now());
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= 5) {
                user.setLocked(true);
            }
        }
        setAuditFields(user);
        userFacade.edit(user);
    }
    // </editor-fold>
}
