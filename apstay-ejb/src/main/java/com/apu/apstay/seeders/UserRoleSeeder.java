package com.apu.apstay.seeders;

import com.apu.apstay.entities.Role;
import com.apu.apstay.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 *
 * @author alexc
 */
@Stateless
public class UserRoleSeeder {
    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;
    
    @Transactional
    public void seed() {
        String[] userEmails = {
            "superman@apstay.net", 
            "batman@apstay.net",
            "johnr@apstay.net",
            "aliceh@apstay.net",
            "samg@apstay.net",
            "sarahl@apstay.net",
            "miket@apstay.net"
        };
        String[] roleNames = {
            "superuser", 
            "manager",
            "resident",
            "resident",
            "security",
            "security",
            "security"
        };

        if (userEmails.length != roleNames.length) {
            throw new IllegalArgumentException("User emails and role names arrays must be of the same length!");
        }
        
        for (int i = 0; i < userEmails.length; i++) {
            var email = userEmails[i];
            var roleName = roleNames[i];

            var user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                          .setParameter("email", email)
                          .getSingleResult();

            var role = em.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                          .setParameter("name", roleName)
                          .getSingleResult();

            if (!user.getRoles().contains(role)) {
                user.getRoles().add(role);
                em.merge(user);
            }
        }
    }
}
