package com.apu.apstay.facades;

import com.apu.apstay.entities.VisitCode;
import com.apu.apstay.entities.VisitRequest;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

/**
 *
 * @author alexc
 */
@Stateless
public class VisitCodeFacade extends AbstractFacade<VisitCode> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VisitCodeFacade() {
        super(VisitCode.class);
    }
    
    public VisitCode findByCode(String code) {
        try {
            return em.createQuery(
                "SELECT vc FROM VisitCode vc WHERE vc.code = :code", 
                VisitCode.class
            )
            .setParameter("code", code)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public VisitCode findByVisitRequest(VisitRequest visitRequest) {
        try {
            return em.createQuery(
                    "SELECT vc FROM VisitCode vc WHERE vc.visitRequest = :visitRequest",
                    VisitCode.class
            )
            .setParameter("visitRequest", visitRequest)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public VisitCode findByVisitRequestId(Long visitRequestId) {
        try {
            return em.createQuery(
                    "SELECT vc FROM VisitCode vc WHERE vc.visitRequest.id = :visitRequestId",
                    VisitCode.class
            )
            .setParameter("visitRequestId", visitRequestId)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    public Long getVisitRequestId(String code) {
        try {
            return em.createQuery(
                    "SELECT vc.visitRequest.id FROM VisitCode vc WHERE vc.code = :code",
                    Long.class
            )
            .setParameter("code", code)
            .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
