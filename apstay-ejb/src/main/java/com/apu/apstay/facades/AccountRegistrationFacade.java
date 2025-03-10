package com.apu.apstay.facades;

import com.apu.apstay.entities.AccountRegistration;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author alexc
 */
@Stateless
public class AccountRegistrationFacade extends AbstractFacade<AccountRegistration> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountRegistrationFacade() {
        super(AccountRegistration.class);
    }
    
    public AccountRegistration findWithReviewer(Long id) {
        try {
            return em.createQuery(
                "SELECT ar FROM AccountRegistration ar " +
                "LEFT JOIN FETCH ar.reviewer r " +
                "WHERE ar.id = :id", AccountRegistration.class)
                .setParameter("id", id)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
