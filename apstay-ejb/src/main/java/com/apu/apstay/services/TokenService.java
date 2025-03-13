package com.apu.apstay.services;

import com.apu.apstay.entities.Token;
import com.apu.apstay.entities.User;
import com.apu.apstay.enums.TokenType;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.facades.TokenFacade;
import com.apu.apstay.services.common.BaseService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author alexc
 */
@Stateless
public class TokenService extends BaseService {
    
    @EJB
    private TokenFacade tokenFacade;
    
    @EJB
    private TokenFactory tokenFactory;
    
    /**
     * Creates a password reset token for the given user
     * @param user The user to create a token for
     * @return The generated token string
     */
    public String createPasswordResetToken(User user) {
        tokenFacade.invalidateUserTokens(user, TokenType.PASSWORD_RESET);

        var tokenString = generateRandomToken();

        var token = tokenFactory.getNew();
        token.setUser(user);
        token.setToken(tokenString);
        token.setTokenType(TokenType.PASSWORD_RESET);
        token.setExpiresAt(LocalDateTime.now().plusHours(24));
        setAuditFields(token);
        
        tokenFacade.create(token);
        return tokenString;
    }

    public Token validatePasswordResetToken(String tokenString) throws BusinessRulesException {
        if (tokenString == null || tokenString.trim().isEmpty()) {
            throw new BusinessRulesException("token", "Token is required");
        }
        
        var token = tokenFacade.findValidToken(tokenString, TokenType.PASSWORD_RESET);
        if (token == null) {
            throw new BusinessRulesException("token", "Invalid or expired token");
        }
        
        return token;
    }

    public void markTokenAsUsed(Token token) {
        token.setUsedAt(LocalDateTime.now());
        setAuditFields(token);
        tokenFacade.edit(token);
    }
    
    private String generateRandomToken() {
        return UUID.randomUUID().toString();
    }
}
