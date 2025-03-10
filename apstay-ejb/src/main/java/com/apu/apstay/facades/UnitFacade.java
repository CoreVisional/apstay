package com.apu.apstay.facades;

import com.apu.apstay.entities.Unit;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author alexc
 */
@Stateless
public class UnitFacade extends AbstractFacade<Unit> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UnitFacade() {
        super(Unit.class);
    }
    
    public Unit findByUnitName(String unitName) {
        try {
            return em.createQuery("SELECT u FROM Unit u WHERE u.unitName = :unitName", Unit.class)
                     .setParameter("unitName", unitName)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
