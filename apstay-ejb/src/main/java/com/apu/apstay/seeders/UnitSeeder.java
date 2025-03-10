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
        String[][] units = {
            {"A-01-01", "1"},
            {"A-02-02", "2"},
            {"B-01-01", "1"},
            {"B-02-02", "2"}
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
                unit.setOccupied(false);
                unit.setActive(true);
                unit.setResidents(new HashSet<>());
                em.persist(unit);
            }
        }
    }
}
