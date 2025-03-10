package com.apu.apstay.commands.users;

/**
 *
 * @author alexc
 */
public record UserCreateCommand (
        String username,
        String email,
        String password
) { }
