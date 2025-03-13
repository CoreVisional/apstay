package com.apu.apstay.services;

import com.apu.apstay.entities.Token;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class TokenFactory {
    
    public Token getNew() {
        return new Token();
    }
}
