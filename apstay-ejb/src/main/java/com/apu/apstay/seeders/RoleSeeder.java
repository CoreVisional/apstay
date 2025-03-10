package com.apu.apstay.seeders;

import com.apu.apstay.entities.Role;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author alexc
 */
@Stateless
public class RoleSeeder {
    
    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;
    
    public void seed() {
        // Check if roles are already seeded
        List<Role> existingRoles = em.createQuery("SELECT role FROM Role role", Role.class)
                                     .getResultList();
        if (!existingRoles.isEmpty()) {
            return;
        }
        
        var superUser = new Role();
        superUser.setName("superuser");
        em.persist(superUser);

        var manager = new Role();
        manager.setName("manager");
        em.persist(manager);
        
        var securityStaff = new Role();
        securityStaff.setName("security");
        em.persist(securityStaff);

        var resident = new Role();
        resident.setName("resident");
        em.persist(resident);
    }
}
