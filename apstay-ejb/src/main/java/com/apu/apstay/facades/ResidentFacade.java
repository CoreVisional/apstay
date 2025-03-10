package com.apu.apstay.facades;

import com.apu.apstay.entities.Resident;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 *
 * @author alexc
 */
@Stateless
public class ResidentFacade extends AbstractFacade<Resident> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResidentFacade() {
        super(Resident.class);
    }
    
    public Resident findByUserId(Long userId) {
        try {
            return em.createQuery(
                "SELECT r FROM Resident r WHERE r.user.id = :userId", 
                Resident.class
            )
            .setParameter("userId", userId)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public List<Resident> findAllActive() {
        try {
            return getEntityManager()
                .createQuery("SELECT r FROM Resident r JOIN r.user u WHERE u.active = true", Resident.class)
                .getResultList();   
        } catch (NoResultException e) {
            return null;
        }
    }
}
