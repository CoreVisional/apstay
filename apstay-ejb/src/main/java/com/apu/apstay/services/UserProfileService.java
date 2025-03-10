package com.apu.apstay.services;

import com.apu.apstay.dtos.UserProfileDto;
import com.apu.apstay.enums.Gender;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.facades.UserProfileFacade;
import com.apu.apstay.utils.EncryptionUtil;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
@Stateless
public class UserProfileService {
    
    @EJB
    private UserProfileFacade userProfileFacade;
    
    @EJB
    private UserProfileFactory userProfileFactory;
    
    // <editor-fold defaultstate="collapsed" desc="Implementations of UserService">
    public UserProfileDto getByUserId(Long userId) {
        var _profile = userProfileFacade.findByUserId(userId);
        return _profile != null ? userProfileFactory.toDto(_profile) : null;
    }
    
    public List<UserProfileDto> getByUserIds(List<Long> userIds) {
        var _profiles = userProfileFacade.findByUserIds(userIds);
        
        return _profiles.stream()
            .map(userProfileFactory::toDto)
            .collect(Collectors.toList());
    }
    
    public void updateProfile(Long userId, String name, String identityNumber, 
                              Gender gender, String phone, String address) throws BusinessRulesException {
        var profile = userProfileFacade.findByUserId(userId);

        if (identityNumber != null && !identityNumber.isEmpty()) {
            var existingProfile = userProfileFacade.findByIdentityNumber(identityNumber);
            if (existingProfile != null && !existingProfile.getUser().getId().equals(userId)) {
                throw new BusinessRulesException("identityNumber", "Identity number is already registered!");
            }
        }

        profile.setName(name);
        profile.setIdentityNumber(EncryptionUtil.encrypt(identityNumber));
        profile.setGender(gender);
        profile.setPhone(phone);
        profile.setAddress(address);

        userProfileFacade.edit(profile);
    }
    // </editor-fold>
}
