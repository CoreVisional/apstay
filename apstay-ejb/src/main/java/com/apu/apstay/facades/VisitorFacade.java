package com.apu.apstay.facades;

import com.apu.apstay.entities.Visitor;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author alexc
 */
@Stateless
public class VisitorFacade extends AbstractFacade<Visitor> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VisitorFacade() {
        super(Visitor.class);
    }

    public Visitor findByIdentityNumber(String identityNumber) {
        try {
            return em.createQuery("SELECT v FROM Visitor v WHERE v.identityNumber = :identityNumber", Visitor.class)
                     .setParameter("identityNumber", identityNumber)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
