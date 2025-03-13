package com.apu.apstay.facades;

import com.apu.apstay.entities.UserProfile;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import java.util.List;

/**
 *
 * @author alexc
 */
@Stateless
public class UserProfileFacade extends AbstractFacade<UserProfile> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserProfileFacade() {
        super(UserProfile.class);
    }
    
    public List<UserProfile> findByUserIds(List<Long> userIds) {
        return getEntityManager()
            .createQuery("SELECT p FROM UserProfile p WHERE p.user.id IN :userIds", UserProfile.class)
            .setParameter("userIds", userIds)
            .getResultList();
    }
    
    public UserProfile findByUserId(Long userId) {
        return getEntityManager()
                .createQuery("SELECT p FROM UserProfile p WHERE p.user.id = :userId", UserProfile.class)
                .setParameter("userId", userId)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
    
    public UserProfile findByIdentityNumber(String identityNumber) {
        return getEntityManager()
                .createQuery("SELECT up FROM UserProfile up WHERE up.identityNumber = :identityNumber", UserProfile.class)
                .setParameter("identityNumber", identityNumber)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }
    
    public List<Object[]> getGenderDistributionData() {
        return getEntityManager()
                .createQuery("SELECT up.gender, COUNT(up) FROM UserProfile up GROUP BY up.gender", Object[].class)
                .getResultList();
    }
}
