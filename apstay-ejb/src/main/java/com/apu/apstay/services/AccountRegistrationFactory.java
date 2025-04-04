package com.apu.apstay.services;

import com.apu.apstay.dtos.AccountRegistrationDto;
import com.apu.apstay.entities.AccountRegistration;
import com.apu.apstay.utils.EncryptionUtil;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class AccountRegistrationFactory {
    
    public AccountRegistration getNew() {
        return new AccountRegistration();
    }
    
    public AccountRegistrationDto toDto(AccountRegistration entity) {
        if (entity == null) {
            return null;
        }
        
        var registrantId = entity.getRegistrant() != null ? entity.getRegistrant().getId() : null;
        var reviewerId = entity.getReviewer() != null ? entity.getReviewer().getId() : null;
        var unitId = entity.getUnit() != null ? entity.getUnit().getId() : null;
        var unitName = entity.getUnit() != null ? entity.getUnit().getUnitName() : null;
        var floorNumber = entity.getUnit() != null ? entity.getUnit().getFloorNumber() : null;
        var identityNumber = EncryptionUtil.decrypt(entity.getIdentityNumber());
        
        return new AccountRegistrationDto(
                entity.getId(),
                registrantId,
                reviewerId,
                unitId,
                null,
                entity.getName(),
                identityNumber,
                entity.getEmail(),
                entity.getGender(),
                entity.getPhone(),
                entity.getAddress(),
                unitName,
                floorNumber,
                entity.getStatus(),
                entity.getRemarks(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}
