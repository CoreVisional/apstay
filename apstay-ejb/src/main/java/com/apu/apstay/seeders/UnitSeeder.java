package com.apu.apstay.seeders;

import com.apu.apstay.entities.Unit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashSet;

/**
 *
 * @author alexc
 */
public class UnitSeeder {
    
    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;
    
    public void seed() {
        // Format: [Unit Name, Floor Number, Capacity]
        String[][] units = {
            {"A-01", "1", "2"},
            {"A-02", "2", "2"},
            {"B-01", "1", "3"},
            {"B-02", "2", "4"},
            {"A-03", "3", "2"},
            {"A-04", "4", "2"},
            {"A-05", "5", "1"},
            {"A-06", "6", "1"},
            {"B-03", "3", "3"},
            {"B-04", "4", "2"},
            {"B-05", "5", "2"},
            {"C-01", "1", "1"},
            {"C-02", "2", "2"},
            {"C-03", "3", "1"},
            {"C-04", "4", "2"}
        };
        
        for (String[] unitData : units) {
            var existingUnit = em.createQuery(
                "SELECT u FROM Unit u WHERE u.unitName = :unitName", 
                Unit.class
            )
            .setParameter("unitName", unitData[0])
            .getResultList();
            
            if (existingUnit.isEmpty()) {
                var unit = new Unit();
                unit.setUnitName(unitData[0]);
                unit.setFloorNumber(Integer.parseInt(unitData[1]));
                unit.setCapacity(Integer.parseInt(unitData[2]));
                unit.setActive(true);
                unit.setResidents(new HashSet<>());
                em.persist(unit);
            }
        }
    }
}