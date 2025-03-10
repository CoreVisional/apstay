package com.apu.apstay.facades;

import com.apu.apstay.entities.Security;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author alexc
 */
@Stateless
public class SecurityFacade extends AbstractFacade<Security> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecurityFacade() {
        super(Security.class);
    }
    
    public Security findByUserId(Long userId) {
        try {
            return em.createQuery(
                "SELECT s FROM Security s WHERE s.user.id = :userId", 
                Security.class
            )
            .setParameter("userId", userId)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
