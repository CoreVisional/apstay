package com.apu.apstay.facades;

import com.apu.apstay.entities.Unit;
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

    public List<Unit> findAllWithAvailableSpace() {
        return em.createQuery(
            "SELECT u FROM Unit u WHERE u.active = true AND u.id IN " +
            "(SELECT r.unit.id FROM Resident r GROUP BY r.unit.id HAVING COUNT(r) < " +
            "(SELECT u2.capacity FROM Unit u2 WHERE u2.id = r.unit.id))" +
            "OR u.id NOT IN (SELECT DISTINCT r.unit.id FROM Resident r)",
            Unit.class
        ).getResultList();
    }

    public int countActiveUnits() {
        return ((Number) em.createQuery("SELECT COUNT(u) FROM Unit u WHERE u.active = true")
                .getSingleResult()).intValue();
    }

    public int countInactiveUnits() {
        return ((Number) em.createQuery("SELECT COUNT(u) FROM Unit u WHERE u.active = false")
                .getSingleResult()).intValue();
    }

    public int countVacantUnits() {
        return ((Number) em.createQuery(
                "SELECT COUNT(u) FROM Unit u WHERE u.active = true AND " +
                "(u.id NOT IN (SELECT DISTINCT r.unit.id FROM Resident r) OR " +
                "u.id IN (SELECT r.unit.id FROM Resident r JOIN r.user user WHERE user.active = true GROUP BY r.unit.id HAVING COUNT(r) = 0))")
                .getSingleResult()).intValue();
    }

    public int countFullyOccupiedUnits() {
        return ((Number) em.createQuery(
                "SELECT COUNT(u) FROM Unit u WHERE u.active = true AND " +
                "u.id IN (SELECT r.unit.id FROM Resident r JOIN r.user user WHERE user.active = true " +
                "GROUP BY r.unit.id HAVING COUNT(r) = " +
                "(SELECT u2.capacity FROM Unit u2 WHERE u2.id = r.unit.id))")
                .getSingleResult()).intValue();
    }

    public List<Object[]> getOccupancyRateByFloor() {
        return em.createQuery(
                "SELECT u.floorNumber, " +
                "COALESCE(SUM(CASE WHEN res.unit.id IS NOT NULL THEN 1 ELSE 0 END), 0) as occupied, " +
                "COUNT(u) as total " +
                "FROM Unit u " +
                "LEFT JOIN u.residents res " +
                "WHERE u.active = true " +
                "GROUP BY u.floorNumber " +
                "ORDER BY u.floorNumber")
                .getResultList();
    }

    public List<Object[]> getUnitCapacityDistribution() {
        return em.createQuery(
                "SELECT u.capacity, COUNT(u) " +
                "FROM Unit u " +
                "WHERE u.active = true " +
                "GROUP BY u.capacity " +
                "ORDER BY u.capacity")
                .getResultList();
    }
}
