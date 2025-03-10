package com.apu.apstay.seeders;

import com.apu.apstay.entities.Security;
import com.apu.apstay.entities.User;
import com.apu.apstay.enums.CommuteMode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author alexc
 */
public class SecuritySeeder {
    
    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;
    
    public void seed() {
        String[] securityEmails = {
            "samg@apstay.net",
            "sarahl@apstay.net",
            "miket@apstay.net"
        };

        CommuteMode[] commuteModes = {
            CommuteMode.PUBLIC_TRANSPORT,
            CommuteMode.DRIVING,
            CommuteMode.WALKING
        };
        
        if (securityEmails.length != commuteModes.length) {
            throw new IllegalArgumentException("Security emails and commute modes arrays must be of the same length!");
        }

        var existingSecurity = em.createQuery("SELECT s FROM Security s", Security.class)
                                           .getResultList();
        if (!existingSecurity.isEmpty()) {
            return;
        }
        
        for (int i = 0; i < securityEmails.length; i++) {
            String email = securityEmails[i];
            var commuteMode = commuteModes[i];

            var user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                         .setParameter("email", email)
                         .getSingleResult();

            var security = new Security();
            security.setUser(user);
            security.setCommuteMode(commuteMode);
            em.persist(security);
        }
    }
}
