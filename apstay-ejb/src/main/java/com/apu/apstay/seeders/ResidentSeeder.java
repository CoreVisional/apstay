package com.apu.apstay.seeders;

import com.apu.apstay.entities.Resident;
import com.apu.apstay.entities.Unit;
import com.apu.apstay.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author alexc
 */
public class ResidentSeeder {
    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;
    
    public void seed() {
        var units = em.createQuery(
            "SELECT u FROM Unit u WHERE u.occupied = false ORDER BY u.unitName", 
            Unit.class
        ).getResultList();
        
        if (units.isEmpty()) {
            return;
        }

        var residentUsers = em.createQuery(
            "SELECT DISTINCT u FROM User u JOIN u.roles r WHERE r.name = :roleName", 
            User.class
        )
        .setParameter("roleName", "resident")
        .getResultList();
        
        int unitIndex = 0;
        for (User user : residentUsers) {
            var existingResident = em.createQuery(
                "SELECT r FROM Resident r WHERE r.user.id = :userId", 
                Resident.class
            )
            .setParameter("userId", user.getId())
            .getResultList();
            
            if (existingResident.isEmpty() && unitIndex < units.size()) {
                var unit = units.get(unitIndex);
                
                var resident = new Resident();
                resident.setUser(user);
                resident.setUnit(unit);
                em.persist(resident);

                unit.setOccupied(true);
                em.merge(unit);
                
                unitIndex++;
            }
        }
    }
}
