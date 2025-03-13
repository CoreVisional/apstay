package com.apu.apstay.facades;

import com.apu.apstay.entities.Resident;
import com.apu.apstay.entities.VisitRequest;
import com.apu.apstay.enums.VisitRequestStatus;
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

    public int countVisitRequests() {
        return ((Number) em.createQuery("SELECT COUNT(vr) FROM VisitRequest vr")
                .getSingleResult()).intValue();
    }

    public int countVisitRequestsByStatus(VisitRequestStatus status) {
        return ((Number) em.createQuery(
                "SELECT COUNT(vr) FROM VisitRequest vr WHERE vr.status = :status")
                .setParameter("status", status)
                .getSingleResult()).intValue();
    }

    public List<Object[]> getVisitRequestsByDayOfWeek() {
        return em.createQuery(
                "SELECT FUNCTION('DAYOFWEEK', vr.arrivalDateTime) - 1 as dayOfWeek, COUNT(vr) " +
                "FROM VisitRequest vr " +
                "GROUP BY FUNCTION('DAYOFWEEK', vr.arrivalDateTime) - 1 " +
                "ORDER BY dayOfWeek")
                .getResultList();
    }

    public List<Object[]> getMostVisitedUnits(int limit) {
        return em.createQuery(
                "SELECT " +
                "   res.unit.unitName, " +
                "   res.unit.floorNumber, " +
                "   COUNT(vr) as totalVisits, " +
                "   SUM(CASE WHEN vr.status = :approvedStatus THEN 1 ELSE 0 END) as approvedVisits, " +
                "   SUM(CASE WHEN vr.status = :rejectedStatus THEN 1 ELSE 0 END) as rejectedVisits " +
                "FROM VisitRequest vr " +
                "JOIN vr.resident res " +
                "GROUP BY res.unit.unitName, res.unit.floorNumber " +
                "ORDER BY totalVisits DESC")
                .setParameter("approvedStatus", VisitRequestStatus.REACHED)
                .setParameter("rejectedStatus", VisitRequestStatus.CANCELLED)
                .setMaxResults(limit)
                .getResultList();
    }
}
