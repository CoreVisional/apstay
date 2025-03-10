package com.apu.apstay.facades;

import com.apu.apstay.entities.Resident;
import com.apu.apstay.entities.VisitRequest;
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
public class VisitRequestFacade extends AbstractFacade<VisitRequest> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VisitRequestFacade() {
        super(VisitRequest.class);
    }
    
    public List<VisitRequest> findByResident(Resident resident) {
        try {
            return em.createQuery(
                    "SELECT vr FROM VisitRequest vr WHERE vr.resident = :resident ORDER BY vr.arrivalDateTime DESC",
                    VisitRequest.class
                )
                .setParameter("resident", resident)
                .getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }
}
