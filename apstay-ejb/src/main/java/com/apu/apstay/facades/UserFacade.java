package com.apu.apstay.facades;

import com.apu.apstay.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

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
        String baseQuery = "SELECT DISTINCT u FROM User u JOIN FETCH u.roles r WHERE r.name IN ('manager', 'security')";
        
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
}
