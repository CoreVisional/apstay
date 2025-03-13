package com.apu.apstay.seeders;

import com.apu.apstay.entities.Resident;
import com.apu.apstay.entities.Unit;
import com.apu.apstay.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashSet;

/**
 *
 * @author alexc
 */
public class ResidentSeeder {
    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;
    
    public void seed() {
        var units = em.createQuery(
            "SELECT u FROM Unit u LEFT JOIN FETCH u.residents WHERE u.active = true ORDER BY u.unitName", 
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

                int occupancy = unit.getResidents() != null ? unit.getResidents().size() : 0;
                boolean hasSpace = occupancy < unit.getCapacity();

                if (hasSpace) {
                    var resident = new Resident();
                    resident.setUser(user);
                    resident.setUnit(unit);

                    if (unit.getResidents() == null) {
                        unit.setResidents(new HashSet<>());
                    }
                    unit.getResidents().add(resident);
                    
                    em.persist(resident);
                }
                
                unitIndex++;
            }
        }
    }
}