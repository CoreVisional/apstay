package com.apu.apstay.facades;

import com.apu.apstay.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author alexc
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    public List<User> findByUsernameOrEmail(String loginKey) {
        return getEntityManager()
            .createQuery("SELECT u FROM User u WHERE u.email = :loginKey OR u.username = :loginKey", User.class)
            .setParameter("loginKey", loginKey)
            .getResultList();
    }
    
    public List<User> findStaffUsers(boolean isSuperUser) {
        var baseQuery = "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r WHERE r.name IN ('manager', 'security')";
        
        if (isSuperUser) {
            baseQuery += " OR r.name = 'superuser'";
        }
        
        return getEntityManager()
            .createQuery(baseQuery, User.class)
            .getResultList();
    }
    
    public User findByUsername(String username) {
        return getEntityManager()
                .createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public User findByEmail(String email) {
        return getEntityManager()
                .createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

    public int countActiveUsers() {
        return ((Number) em.createQuery("SELECT COUNT(u) FROM User u WHERE u.active = true")
                .getSingleResult()).intValue();
    }

    public int countLockedUsers() {
        return ((Number) em.createQuery("SELECT COUNT(u) FROM User u WHERE u.locked = true")
                .getSingleResult()).intValue();
    }

    public int countLoginsToday() {
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDateTime startOfDay = today.atStartOfDay();
        java.time.LocalDateTime endOfDay = today.plusDays(1).atStartOfDay().minusNanos(1);

        return ((Number) em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.lastLogin BETWEEN :startOfDay AND :endOfDay")
                .setParameter("startOfDay", startOfDay)
                .setParameter("endOfDay", endOfDay)
                .getSingleResult()).intValue();
    }

    public int countFailedLoginsLastWeek() {
        java.time.LocalDateTime weekAgo = java.time.LocalDateTime.now().minusDays(7);

        return ((Number) em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.lastFailedLogin >= :weekAgo AND u.failedLoginAttempts > 0")
                .setParameter("weekAgo", weekAgo)
                .getSingleResult()).intValue();
    }

    public List<Object[]> getLoginActivityLastWeek() {
        java.time.LocalDateTime weekAgo = java.time.LocalDateTime.now().minusDays(7);

        List<User> recentLogins = em.createQuery(
                "SELECT u FROM User u WHERE u.lastLogin >= :weekAgo")
                .setParameter("weekAgo", weekAgo)
                .getResultList();

        List<User> recentFailedLogins = em.createQuery(
                "SELECT u FROM User u WHERE u.lastFailedLogin >= :weekAgo AND u.failedLoginAttempts > 0")
                .setParameter("weekAgo", weekAgo)
                .getResultList();

        // Process in Java to group by day of week
        Map<Integer, Integer> successByDay = new HashMap<>();
        Map<Integer, Integer> failureByDay = new HashMap<>();

        for (int i = 1; i <= 7; i++) {
            successByDay.put(i, 0);
            failureByDay.put(i, 0);
        }

        for (User user : recentLogins) {
            if (user.getLastLogin() != null) {
                int dayOfWeek = user.getLastLogin().getDayOfWeek().getValue();
                successByDay.put(dayOfWeek, successByDay.get(dayOfWeek) + 1);
            }
        }

        for (User user : recentFailedLogins) {
            if (user.getLastFailedLogin() != null) {
                int dayOfWeek = user.getLastFailedLogin().getDayOfWeek().getValue();
                failureByDay.put(dayOfWeek, failureByDay.get(dayOfWeek) + 1);
            }
        }

        List<Object[]> results = new ArrayList<>();
        for (int day = 1; day <= 7; day++) {
            results.add(new Object[] { day, successByDay.get(day), failureByDay.get(day) });
        }

        return results;
    }

    public List<Object[]> getRecentUserActivity(int limit) {
        return em.createQuery(
                "SELECT " +
                "   u.username, " +
                "   u.lastLogin, " +
                "   u.failedLoginAttempts, " +
                "   u.active, " +
                "   u.locked " +
                "FROM User u " +
                "ORDER BY " +
                "   CASE WHEN u.lastLogin IS NULL THEN 0 ELSE 1 END DESC, " +
                "   u.lastLogin DESC")
                .setMaxResults(limit)
                .getResultList();
    }
}
