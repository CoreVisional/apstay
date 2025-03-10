package com.apu.apstay.facades;

import com.apu.apstay.entities.Role;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author alexc
 */
@Stateless
public class RoleFacade extends AbstractFacade<Role> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RoleFacade() {
        super(Role.class);
    }
    
    public Role findByName(String name) {
        try {
            return getEntityManager()
                .createQuery("SELECT r FROM Role r WHERE LOWER(r.name) = LOWER(:name)", Role.class)
                .setParameter("name", name)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
