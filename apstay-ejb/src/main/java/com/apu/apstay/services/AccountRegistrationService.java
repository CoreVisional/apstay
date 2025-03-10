package com.apu.apstay.services;

import com.apu.apstay.services.common.BaseService;
import com.apu.apstay.commands.auth.RegistrationCreateCommand;
import com.apu.apstay.dtos.AccountRegistrationDto;
import com.apu.apstay.enums.ApprovalStatus;
import com.apu.apstay.enums.Gender;
import com.apu.apstay.facades.AccountRegistrationFacade;
import com.apu.apstay.facades.ResidentFacade;
import com.apu.apstay.facades.UnitFacade;
import com.apu.apstay.facades.UserFacade;
import com.apu.apstay.facades.UserProfileFacade;
import com.apu.apstay.utils.EncryptionUtil;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
@Stateless
public class AccountRegistrationService extends BaseService {
    
    @EJB
    private UserFacade userFacade;
    
    @EJB
    private AccountRegistrationFacade accountRegistrationFacade;
    
    @EJB
    private AccountRegistrationFactory accountRegistrationFactory;
    
    @EJB
    private UserProfileService userProfileService;
    
    @EJB
    private ResidentFacade residentFacade;
    
    @EJB
    private UnitFacade unitFacade;
    
    @EJB
    private UserProfileFactory userProfileFactory;
    
    @EJB
    private UserProfileFacade userProfileFacade;
    
    @EJB
    private ResidentFactory residentFactory;

    public List<AccountRegistrationDto> getAll() {
        var _registrations = accountRegistrationFacade.findAll();
        return _registrations.stream()
                .map(accountRegistrationFactory::toDto)
                .collect(Collectors.toList());
    }
    
    public AccountRegistrationDto getById(Long id) {

        var _registration = accountRegistrationFacade.findWithReviewer(id);

        var _dto = accountRegistrationFactory.toDto(_registration);

        var _reviewer = _registration.getReviewer();

        if (_reviewer == null) {
            return _dto;
        }

        var _profile = userProfileService.getByUserId(_reviewer.getId());

        return new AccountRegistrationDto(
                _dto.id(),
                _dto.registrant_id(),
                _dto.reviewerId(),
                _dto.unitId(),
                _profile.name(),
                _dto.name(),
                _dto.identityNumber(),
                _dto.email(),
                _dto.gender(),
                _dto.phone(),
                _dto.address(),
                _dto.unitName(),
                _dto.status(),
                _dto.remarks(),
                _dto.createdAt(),
                _dto.createdBy(),
                _dto.modifiedAt(),
                _dto.modifiedBy()
        );
    }
    
    public void createAccountRegistration(RegistrationCreateCommand command) {
        var _entity = accountRegistrationFactory.getNew();

        if (command.registrantId() != null) {
            var _registrant = userFacade.find(command.registrantId());
            _entity.setRegistrant(_registrant);
        }

        if (command.unitId() != null) {
            var _unit = unitFacade.find(command.unitId());
            _entity.setUnit(_unit);
        }

        _entity.setName(command.fullName());
        _entity.setIdentityNumber(EncryptionUtil.encrypt(command.identityNumber()));
        _entity.setPhone(command.phone());
        _entity.setEmail(command.email());
        _entity.setGender(command.gender());
        _entity.setAddress(command.address());
        _entity.setStatus(ApprovalStatus.PENDING);
        _entity.setCreatedAt(LocalDateTime.now());
        _entity.setModifiedAt(LocalDateTime.now());

        setAuditFields(_entity);

        accountRegistrationFacade.create(_entity);
    }
    
    public AccountRegistrationDto approve(Long id, Long reviewerId) {
        var _registration = accountRegistrationFacade.find(id);
        var _registrantId = _registration.getRegistrant().getId();
        var profileDto = userProfileService.getByUserId(_registrantId);
        var _unit = _registration.getUnit();

        if (profileDto == null) {
            var _userProfile = userProfileFactory.getNew();
            _userProfile.setUser(_registration.getRegistrant());
            _userProfile.setName(_registration.getName());
            _userProfile.setIdentityNumber(_registration.getIdentityNumber());
            _userProfile.setGender(Gender.valueOf(_registration.getGender()));
            _userProfile.setPhone(_registration.getPhone());
            _userProfile.setAddress(_registration.getAddress());
            setAuditFields(_userProfile);
            userProfileFacade.create(_userProfile);
        }

        var _resident = residentFactory.getNew();
        _resident.setUser(_registration.getRegistrant());
        _resident.setUnit(_unit);
        setAuditFields(_resident);
        residentFacade.create(_resident);

        _unit.setOccupied(true);
        setAuditFields(_unit);
        unitFacade.edit(_unit);

        _registration.setStatus(ApprovalStatus.APPROVED);
        _registration.setReviewer(userFacade.find(reviewerId));
        setAuditFields(_registration);
        accountRegistrationFacade.edit(_registration);

        return accountRegistrationFactory.toDto(_registration);
    }
    
    public AccountRegistrationDto reject(Long id, Long reviewerId, String remarks) {
        
        var _registration = accountRegistrationFacade.find(id);
        
        _registration.setStatus(ApprovalStatus.REJECTED);
        _registration.setReviewer(userFacade.find(reviewerId));
        _registration.setRemarks(remarks);
        setAuditFields(_registration);
        accountRegistrationFacade.edit(_registration);
        
        return accountRegistrationFactory.toDto(_registration);
    }
}
