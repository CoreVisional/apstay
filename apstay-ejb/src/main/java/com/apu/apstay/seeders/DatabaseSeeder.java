package com.apu.apstay.seeders;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

/**
 *
 * @author alexc
 */
@Singleton
@Startup
public class DatabaseSeeder {
    @Inject
    private UserSeeder userSeeder;
    @Inject
    private RoleSeeder roleSeeder;
    @Inject
    private UserRoleSeeder userRoleSeeder;
    @Inject
    private UnitSeeder unitSeeder;
    @Inject
    private ResidentSeeder residentSeeder;
    @Inject
    private SecuritySeeder securitySeeder;
    
    @PostConstruct
    public void init() {
        userSeeder.seed();
        roleSeeder.seed();
        userRoleSeeder.seed();
        unitSeeder.seed();
        residentSeeder.seed();
        securitySeeder.seed();
    }
}
