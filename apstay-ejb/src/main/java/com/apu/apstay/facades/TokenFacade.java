package com.apu.apstay.facades;

import com.apu.apstay.entities.Token;
import com.apu.apstay.entities.User;
import com.apu.apstay.enums.TokenType;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
@Stateless
public class TokenFacade extends AbstractFacade<Token> {

    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TokenFacade() {
        super(Token.class);
    }

    public Token findValidToken(String tokenString, TokenType tokenType) {
        try {
            return em.createQuery(
                "SELECT t FROM Token t WHERE t.token = :token AND t.tokenType = :tokenType " +
                "AND t.expiresAt > :now AND t.usedAt IS NULL", Token.class)
                .setParameter("token", tokenString)
                .setParameter("tokenType", tokenType)
                .setParameter("now", LocalDateTime.now())
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void invalidateUserTokens(User user, TokenType tokenType) {
        em.createQuery(
            "UPDATE Token t SET t.usedAt = :now WHERE t.user = :user AND " +
            "t.tokenType = :tokenType AND t.usedAt IS NULL")
            .setParameter("now", LocalDateTime.now())
            .setParameter("user", user)
            .setParameter("tokenType", tokenType)
            .executeUpdate();
    }
}
