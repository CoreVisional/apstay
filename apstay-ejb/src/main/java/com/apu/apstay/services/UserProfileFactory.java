package com.apu.apstay.services;

import com.apu.apstay.dtos.UserProfileDto;
import com.apu.apstay.entities.UserProfile;
import com.apu.apstay.utils.EncryptionUtil;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class UserProfileFactory {

    public UserProfile getNew() {
        return new UserProfile();
    }
    
    public UserProfileDto toDto(UserProfile entity) {
        if (entity == null) {
            return null;
        }
        return new UserProfileDto(
            entity.getId(),
            entity.getUser().getId(),
            entity.getName(),
            EncryptionUtil.decrypt(entity.getIdentityNumber()),
            entity.getGender(),
            entity.getPhone(),
            entity.getAddress()
        );
    }
}
