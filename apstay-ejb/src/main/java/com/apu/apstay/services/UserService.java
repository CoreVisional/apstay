package com.apu.apstay.services;

import com.apu.apstay.commands.users.StaffSaveCommand;
import com.apu.apstay.services.common.BaseService;
import com.apu.apstay.commands.users.UserCreateCommand;
import com.apu.apstay.dtos.UserDto;
import com.apu.apstay.entities.User;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.facades.ResidentFacade;
import com.apu.apstay.facades.RoleFacade;
import com.apu.apstay.facades.UserFacade;
import com.apu.apstay.facades.UserProfileFacade;
import com.apu.apstay.utils.PasswordUtil;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;
import com.apu.apstay.utils.EncryptionUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author alexc
 */
@Stateless
public class UserService extends BaseService {

    @EJB
    private UserFacade userFacade;

    @EJB
    private UserProfileFacade userProfileFacade;

    @EJB
    private UserFactory userFactory;

    @EJB
    private UserProfileFactory userProfileFactory;

    @EJB
    private RoleFacade roleFacade;
    
    @EJB
    private ResidentFacade residentFacade;

    // <editor-fold defaultstate="collapsed" desc="Implementations of UserService">
    public UserDto getById(Long id) {
        return userFactory.toDto(userFacade.find(id));
    }
    
    public User findByEmail(String email) {
        return userFacade.findByEmail(email);
    }
    
    public UserDto getByLoginKey(String loginKey) {
        var _result = userFacade.findByUsernameOrEmail(loginKey);
        return _result.isEmpty() ? null : userFactory.toDto(_result.get(0));
    }

    public List<UserDto> getStaffUsers(boolean includeSuperusers) {
        var _users = userFacade.findStaffUsers(includeSuperusers);
        return _users.stream()
                .map(userFactory::toDto)
                .collect(Collectors.toList());
    }
    
    public UserDto getByResident(Long residentId) {
        var resident = residentFacade.find(residentId);
        if (resident != null && resident.getUser() != null) {
            return userFactory.toDto(resident.getUser());
        }
        return null;
    }

    public User createResident(UserCreateCommand command) {
        var _user = createNewUser(command.username(), command.email(), command.password());
        userFacade.create(_user);
        
        var residentRole = roleFacade.findByName("resident");
        _user.getRoles().add(residentRole);
        
        return _user;
    }

    public UserDto createStaff(StaffSaveCommand command) throws BusinessRulesException {
        
        validateStaffCommand(command, null);

        var _user = createNewUser(command.username(), command.email(), command.identityNumber());

        var _role = roleFacade.find(command.roleId());
        if (_role == null) {
            throw new BusinessRulesException("role", "Invalid role selected!");
        }
        _user.getRoles().add(_role);
        userFacade.create(_user);

        saveStaffProfile(_user, command);

        return userFactory.toDto(_user);
    }
    
    public UserDto updateStaff(Long staffId, StaffSaveCommand command) throws BusinessRulesException {
        validateStaffCommand(command, staffId);

        var user = userFacade.find(staffId);
        if (user == null) {
            throw new BusinessRulesException("user", "Staff user not found");
        }

        user.setUsername(command.username());
        user.setEmail(command.email());
        setAuditFields(user);

        user.getRoles().clear();
        var role = roleFacade.find(command.roleId());
        if (role == null) {
            throw new BusinessRulesException("role", "Invalid role selected!");
        }
        user.getRoles().add(role);

        userFacade.edit(user);

        saveStaffProfile(user, command);

        return userFactory.toDto(user);
    }
    
    public void updateUserAccount(Long userId, String username, String email) throws BusinessRulesException {
        
        var user = userFacade.find(userId);
        
        if (username != null && !username.isEmpty()) {
            var existingUser = userFacade.findByUsername(username);
            if (existingUser != null && !existingUser.getId().equals(userId)) {
                throw new BusinessRulesException("username", "Username is already taken!");
            }
        }
        
        if (email != null && !email.isEmpty()) {
            var existingUser = userFacade.findByEmail(email);
            if (existingUser != null && !existingUser.getId().equals(userId)) {
                throw new BusinessRulesException("email", "Email is already registered!");
            }
        }
        
        user.setUsername(username);
        user.setEmail(email);
        setAuditFields(user);
        userFacade.edit(user);
    }
    
    public void deactivateUser(Long userId) {
        var user = userFacade.find(userId);

        user.setActive(false);
        setAuditFields(user);
        userFacade.edit(user);
    }
    
    public void reactivateUser(Long userId) {
        var user = userFacade.find(userId);

        user.setActive(true);
        setAuditFields(user);
        userFacade.edit(user);
    }
    
    public void changePassword(Long userId, String currentPassword, String newPassword) throws BusinessRulesException {
        var user = userFacade.find(userId);

        if (!PasswordUtil.verifyPassword(currentPassword, user.getPasswordHash())) {
            throw new BusinessRulesException("currentPassword", "Current password is incorrect");
        }

        if (currentPassword.equals(newPassword)) {
            throw new BusinessRulesException("newPassword", "New password must be different from current password");
        }

        if (newPassword.length() < 8) {
            throw new BusinessRulesException("newPassword", "Password must be at least 8 characters long");
        }

        user.setPasswordHash(PasswordUtil.hashPassword(newPassword));
        setAuditFields(user);
        userFacade.edit(user);
    }

    public void resetPassword(User user, String newPassword) throws BusinessRulesException {
        if (newPassword.length() < 8) {
            throw new BusinessRulesException("newPassword", "Password must be at least 8 characters long");
        }

        user.setPasswordHash(PasswordUtil.hashPassword(newPassword));
        setAuditFields(user);
        userFacade.edit(user);
    }
    
    public Map<String, Object> getUserActivityReportData() {
        Map<String, Object> reportData = new HashMap<>();

        int totalUsers = userFacade.count();
        int activeUsers = userFacade.countActiveUsers();
        int lockedUsers = userFacade.countLockedUsers();
        int inactiveUsers = totalUsers - activeUsers - lockedUsers;
        int loginsToday = userFacade.countLoginsToday();
        int failedLoginsLastWeek = userFacade.countFailedLoginsLastWeek();

        reportData.put("totalUsers", totalUsers);
        reportData.put("activeUsers", activeUsers);
        reportData.put("lockedUsers", lockedUsers);
        reportData.put("inactiveUsers", inactiveUsers);
        reportData.put("loginsToday", loginsToday);
        reportData.put("failedLoginsLastWeek", failedLoginsLastWeek);

        List<Integer> statusDistribution = Arrays.asList(
            activeUsers, 
            inactiveUsers,
            lockedUsers
        );
        reportData.put("statusDistribution", statusDistribution);

        List<Object[]> loginActivity = userFacade.getLoginActivityLastWeek();

        String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        int[] successfulLogins = new int[7];
        int[] failedLogins = new int[7];

        for (Object[] dayData : loginActivity) {
            int dayIndex = ((Number) dayData[0]).intValue();
            int successful = ((Number) dayData[1]).intValue();
            int failed = ((Number) dayData[2]).intValue();
            int adjustedIndex = (dayIndex + 5) % 7;
            successfulLogins[adjustedIndex] = successful;
            failedLogins[adjustedIndex] = failed;
        }

        reportData.put("dayLabels", Arrays.asList(dayNames));
        reportData.put("successfulLogins", Arrays.stream(successfulLogins).boxed().collect(Collectors.toList()));
        reportData.put("failedLogins", Arrays.stream(failedLogins).boxed().collect(Collectors.toList()));

        List<Object[]> recentActivity = userFacade.getRecentUserActivity(20);
        List<Map<String, Object>> userActivity = new ArrayList<>();

        for (Object[] userData : recentActivity) {
            Map<String, Object> user = new HashMap<>();

            String username = (String) userData[0];
            java.time.LocalDateTime lastLogin = (java.time.LocalDateTime) userData[1];
            int failedAttempts = ((Number) userData[2]).intValue();
            boolean active = (boolean) userData[3];
            boolean locked = (boolean) userData[4];

            user.put("username", username);
            user.put("lastLogin", lastLogin);
            user.put("failedAttempts", failedAttempts);

            String status;
            if (locked) {
                status = "Locked";
            } else if (active) {
                status = "Active";
            } else {
                status = "Inactive";
            }
            user.put("status", status);

            userActivity.add(user);
        }

        reportData.put("userActivity", userActivity);

        return reportData;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Private Implementations">
    private void validateStaffCommand(StaffSaveCommand command, Long existingUserId) throws BusinessRulesException {
        validateBusinessRules(command.username(), command.email(), command.identityNumber(), existingUserId);

        var _role = roleFacade.find(command.roleId());
        if (_role == null) {
            throw new BusinessRulesException("role", "Invalid role selected!");
        }
    }
    
    private void validateBusinessRules(String username, String email, String identityNumber, Long existingUserId) throws BusinessRulesException {
        var _username = (username != null && !username.isEmpty()) ? userFacade.findByUsername(username) : null;
        if (_username != null && (existingUserId == null || !_username.getId().equals(existingUserId))) {
            throw new BusinessRulesException("username", "Username is already taken!");
        }

        var _userEmail = (email != null && !email.isEmpty()) ? userFacade.findByEmail(email) : null;
        if (_userEmail != null && (existingUserId == null || !_userEmail.getId().equals(existingUserId))) {
            throw new BusinessRulesException("email", "Email is already registered!");
        }

        var _identityNumber = (identityNumber != null && !identityNumber.isEmpty()) ? userProfileFacade.findByIdentityNumber(identityNumber) : null;
        if (_identityNumber != null && (existingUserId == null || !_identityNumber.getUser().getId().equals(existingUserId))) {
            throw new BusinessRulesException("identityNumber", "An account with the provided details already exists!");
        }
    }
    
    private User createNewUser(String username, String email, String password) {
        var _user = userFactory.getNew();
        _user.setUsername(username);
        _user.setEmail(email);
        _user.setPasswordHash(PasswordUtil.hashPassword(password));
        setAuditFields(_user);
        
        return _user;
    }
    
    private void saveStaffProfile(User user, StaffSaveCommand command) {
        var profile = userProfileFacade.findByUserId(user.getId());

        if (profile == null) {
            profile = userProfileFactory.getNew();
            profile.setUser(user);
            user.setUserProfile(profile);
        }

        profile.setName(command.fullName());
        profile.setIdentityNumber(EncryptionUtil.encrypt(command.identityNumber()));
        profile.setGender(command.gender());
        profile.setPhone(command.phone());
        profile.setAddress(command.address());
        setAuditFields(profile);

        if (profile.getId() == null) {
            userProfileFacade.create(profile);
        } else {
            userProfileFacade.edit(profile);
        }
    }
    // </editor-fold>
}
