package com.apu.apstay.seeders;

import com.apu.apstay.entities.Resident;
import com.apu.apstay.entities.VisitCode;
import com.apu.apstay.entities.VisitRequest;
import com.apu.apstay.entities.Visitor;
import com.apu.apstay.enums.Gender;
import com.apu.apstay.enums.VisitRequestStatus;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

/**
 *
 * @author alexc
 */
@Stateless
public class VisitRequestSeeder {
    
    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;
    
    public void seed() {

        var requestCount = em.createQuery("SELECT COUNT(vr) FROM VisitRequest vr", Long.class)
                             .getSingleResult();
        
        if (requestCount > 0) {
            return;
        }

        var residents = em.createQuery(
            "SELECT r FROM Resident r JOIN FETCH r.user u JOIN FETCH u.userProfile",
            Resident.class
        ).getResultList();
        
        if (residents.isEmpty()) {
            return;
        }

        String[][] visitorData = {
            {"Thomas Anderson", "123456789012", "MALE", "0123456789"},
            {"Trinity Smith", "234567890123", "FEMALE", "0234567890"},
            {"Morpheus Jones", "345678901234", "MALE", "0345678901"},
            {"Oracle Johnson", "456789012345", "FEMALE", "0456789012"},
            {"Niobe Williams", "567890123456", "FEMALE", "0567890123"}
        };
        
        var random = new Random();
        var now = LocalDateTime.now();

        for (int i = 0; i < 5; i++) {
            String[] vData = visitorData[i];
            var visitor = findOrCreateVisitor(vData[0], vData[1], Gender.valueOf(vData[2]), vData[3]);

            var resident = residents.get(random.nextInt(residents.size()));

            var arrivalTime = now.minusDays(random.nextInt(30) + 1)
                                          .minusHours(random.nextInt(12))
                                          .truncatedTo(ChronoUnit.HOURS);
            
            var request = new VisitRequest();
            request.setVisitor(visitor);
            request.setResident(resident);
            request.setArrivalDateTime(arrivalTime);
            request.setStatus(VisitRequestStatus.REACHED);
            request.setActive(false);
            em.persist(request);

            var code = new VisitCode();
            code.setVisitRequest(request);
            code.setCode(generateCode());
            code.setActive(false);
            em.persist(code);
        }
    }
    
    private Visitor findOrCreateVisitor(String name, String identityNumber, Gender gender, String phone) {
        List<Visitor> existingVisitors = em.createQuery(
            "SELECT v FROM Visitor v WHERE v.identityNumber = :identityNumber",
            Visitor.class
        )
        .setParameter("identityNumber", identityNumber)
        .getResultList();
        
        if (!existingVisitors.isEmpty()) {
            return existingVisitors.get(0);
        }
        
        var visitor = new Visitor();
        visitor.setName(name);
        visitor.setIdentityNumber(identityNumber);
        visitor.setGender(gender);
        visitor.setPhone(phone);
        em.persist(visitor);
        
        return visitor;
    }
    
    private String generateCode() {
        var random = new Random();
        var code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}
