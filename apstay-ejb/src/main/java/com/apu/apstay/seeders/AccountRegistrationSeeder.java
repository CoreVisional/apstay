package com.apu.apstay.seeders;

import com.apu.apstay.entities.AccountRegistration;
import com.apu.apstay.entities.Unit;
import com.apu.apstay.entities.User;
import com.apu.apstay.enums.ApprovalStatus;
import com.apu.apstay.utils.EncryptionUtil;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Random;

/**
 *
 * @author alexc
 */
@Stateless
public class AccountRegistrationSeeder {
    
    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;
    
    public void seed() {
        var registrationCount = em.createQuery("SELECT COUNT(ar) FROM AccountRegistration ar", Long.class)
                                  .getSingleResult();
        
        if (registrationCount > 0) {
            return;
        }

        var managers = em.createQuery(
            "SELECT u FROM User u JOIN u.roles r WHERE r.name = 'manager'",
            User.class
        ).getResultList();
        
        if (managers.isEmpty()) {
            return;
        }
        
        User manager = managers.get(0);

        List<Unit> units = em.createQuery("SELECT u FROM Unit u", Unit.class).getResultList();
        
        if (units.isEmpty()) {
            return;
        }

        var residentUsers = em.createQuery(
            "SELECT u FROM User u JOIN u.roles r WHERE r.name = 'resident'",
            User.class
        ).getResultList();
        
        if (residentUsers.size() < 5) {
            return;
        }

        String[][] registrationData = {
            {"William Brown", "123456789012", "williambrown@test.com", "MALE", "0123456789", "123 Registration St"},
            {"Emma Davis", "234567890123", "emmadavis@test.com", "FEMALE", "0234567890", "456 Application Ave"},
            {"James Wilson", "345678901234", "jameswilson@test.com", "MALE", "0345678901", "789 Signup Blvd"},
            {"Sophia Moore", "456789012345", "sophiamoore@test.com", "FEMALE", "0456789012", "101 Form Lane"},
            {"Daniel Taylor", "567890123456", "danieltaylor@test.com", "MALE", "0567890123", "202 Record Dr"}
        };
        
        var random = new Random();

        for (int i = 0; i < 5; i++) {
            String[] regData = registrationData[i];
            
            var registration = new AccountRegistration();
            registration.setRegistrant(residentUsers.get(i));
            registration.setReviewer(manager);
            registration.setUnit(units.get(random.nextInt(units.size())));
            registration.setName(regData[0]);
            registration.setIdentityNumber(EncryptionUtil.encrypt(regData[1]));
            registration.setEmail(regData[2]);
            registration.setGender(regData[3]);
            registration.setPhone(regData[4]);
            registration.setAddress(regData[5]);
            registration.setStatus(ApprovalStatus.APPROVED);
            registration.setRemarks("Registration approved by system administrator.");
            
            em.persist(registration);
        }
    }
}