package com.apu.apstay.facades;

import com.apu.apstay.entities.AccountRegistration;
import com.apu.apstay.enums.ApprovalStatus;
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
    
    public int countPendingRegistrations() {
        return em.createQuery(
            "SELECT COUNT(ar) FROM AccountRegistration ar WHERE ar.status = :status", Long.class)
            .setParameter("status", ApprovalStatus.PENDING)
            .getSingleResult()
            .intValue();
    }

    public double getAverageApprovalTimeInDays() {
        try {
            return ((Number) em.createQuery(
                    "SELECT AVG(FUNCTION('DATEDIFF', ar.modifiedAt, ar.createdAt)) " +
                    "FROM AccountRegistration ar " +
                    "WHERE ar.status = :approvedStatus")
                    .setParameter("approvedStatus", ApprovalStatus.APPROVED)
                    .getSingleResult()).doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public double getApprovalRate() {
        long totalProcessed = ((Number) em.createQuery(
                "SELECT COUNT(ar) FROM AccountRegistration ar " +
                "WHERE ar.status IN (:approvedStatus, :rejectedStatus)")
                .setParameter("approvedStatus", ApprovalStatus.APPROVED)
                .setParameter("rejectedStatus", ApprovalStatus.REJECTED)
                .getSingleResult()).longValue();

        if (totalProcessed == 0) {
            return 0.0;
        }

        long totalApproved = ((Number) em.createQuery(
                "SELECT COUNT(ar) FROM AccountRegistration ar " +
                "WHERE ar.status = :approvedStatus")
                .setParameter("approvedStatus", ApprovalStatus.APPROVED)
                .getSingleResult()).longValue();

        return (double) totalApproved / totalProcessed * 100.0;
    }

    public int[] getRegistrationStatusCounts() {
        int approved = ((Number) em.createQuery(
                "SELECT COUNT(ar) FROM AccountRegistration ar " +
                "WHERE ar.status = :status")
                .setParameter("status", ApprovalStatus.APPROVED)
                .getSingleResult()).intValue();

        int rejected = ((Number) em.createQuery(
                "SELECT COUNT(ar) FROM AccountRegistration ar " +
                "WHERE ar.status = :status")
                .setParameter("status", ApprovalStatus.REJECTED)
                .getSingleResult()).intValue();

        int pending = countPendingRegistrations();

        return new int[]{approved, rejected, pending};
    }

    public List<Object[]> getRecentRegistrations(int limit) {
        return em.createQuery(
                "SELECT ar.name, u.unitName, " +
                "CASE " +
                "   WHEN ar.status = :pendingStatus THEN null " +
                "   ELSE FUNCTION('DATEDIFF', ar.modifiedAt, ar.createdAt) " +
                "END as daysToProcess, " +
                "ar.status " +
                "FROM AccountRegistration ar " +
                "JOIN ar.unit u " +
                "ORDER BY ar.createdAt DESC")
                .setParameter("pendingStatus", ApprovalStatus.PENDING)
                .setMaxResults(limit)
                .getResultList();
    }
}
