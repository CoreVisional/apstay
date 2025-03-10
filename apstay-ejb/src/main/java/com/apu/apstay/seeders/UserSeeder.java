package com.apu.apstay.seeders;

import com.apu.apstay.entities.User;
import com.apu.apstay.entities.UserProfile;
import com.apu.apstay.enums.Gender;
import com.apu.apstay.utils.EncryptionUtil;
import com.apu.apstay.utils.PasswordUtil;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
@Stateless
public class UserSeeder {
    @PersistenceContext(unitName = "apstay-ejbPU")
    private EntityManager em;
    
    public void seed() {
        try {
            String[][] users = {
                
                // Managing Staff
                {"Supe", "superman@apstay.net", "111", "Clark Kent", "123456781234", "MALE", "0123456789", "Krypton"},
                {"DarkKnight", "batman@apstay.net", "222", "Bruce Wayne", "987654321098", "MALE", "0987654321", "Gotham"},
                
                // Residents
                {"JohnR", "johnr@apstay.net", "333", "John Smith", "741852963741", "MALE", "0111222333", "123 Apple Street, Block A"},
                {"AliceH", "aliceh@apstay.net", "444", "Alice Johnson", "852963741852", "FEMALE", "0444555666", "456 Orange Avenue, Block B"},
                
                // Security Staff
                {"SamG", "samg@apstay.net", "555", "Samuel Garcia", "123456789012", "MALE", "0555666777", "789 Pine Road, Block C"},
                {"SarahL", "sarahl@apstay.net", "666", "Sarah Lee", "234567890123", "FEMALE", "0666777888", "101 Maple Lane, Block D"},
                {"MikeT", "miket@apstay.net", "777", "Michael Thompson", "345678901234", "MALE", "0777888999", "202 Cedar Court, Block E"}
            };
            
            for (String[] userData : users) {
                var user = new User();
                user.setUsername(userData[0]);
                user.setEmail(userData[1]);
                String rawPassword = userData[2];
                String hashedPassword = PasswordUtil.hashPassword(rawPassword);
                user.setPasswordHash(hashedPassword);
                user.setActive(true);
                user.setLocked(false);
                user.setFailedLoginAttempts(0);
                user.setLastLogin(LocalDateTime.now());
                em.persist(user);

                var profile = new UserProfile();
                profile.setUser(user);
                profile.setName(userData[3]);
                profile.setIdentityNumber(EncryptionUtil.encrypt(userData[4]));
                profile.setGender(Gender.valueOf(userData[5]));
                profile.setPhone(userData[6]);
                profile.setAddress(userData[7]);
                em.persist(profile);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting sensitive data", e);
        }
    }
}
