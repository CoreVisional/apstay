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
                
                // Security Staff
                {"SamG", "samg@apstay.net", "555", "Samuel Garcia", "123456789012", "MALE", "0555666777", "789 Pine Road, Block C"},
                {"SarahL", "sarahl@apstay.net", "666", "Sarah Lee", "234567890123", "FEMALE", "0666777888", "101 Maple Lane, Block D"},
                {"MikeT", "miket@apstay.net", "777", "Michael Thompson", "345678901234", "MALE", "0777888999", "202 Cedar Court, Block E"},
                
                // Residents
                {"JohnR", "johnr@apstay.net", "333", "John Smith", "741852963741", "MALE", "0111222333", "123 Apple Street, Block A"},
                {"AliceH", "aliceh@apstay.net", "444", "Alice Johnson", "852963741852", "FEMALE", "0444555666", "456 Orange Avenue, Block B"},
                {"MichaelB", "michaelb@apstay.net", "101", "Michael Brown", "222222222222", "MALE", "0112000001", "789 Banana Road, Block C"},
                {"Emmac", "emmac@apstay.net", "102", "Emma Collins", "222222222223", "FEMALE", "0112000002", "101 Cherry Lane, Block D"},
                {"Liamj", "liamj@apstay.net", "103", "Liam Johnson", "222222222224", "MALE", "0112000003", "202 Peach Street, Block E"},
                {"Oliviad", "oliviad@apstay.net", "104", "Olivia Davis", "222222222225", "FEMALE", "0112000004", "303 Grape Avenue, Block F"},
                {"Noahm", "noahm@apstay.net", "105", "Noah Miller", "222222222226", "MALE", "0112000005", "404 Lemon Blvd, Block G"},
                {"Avagarcia", "avagarcia@apstay.net", "106", "Ava Garcia", "222222222227", "FEMALE", "0112000006", "505 Mango Drive, Block H"},
                {"WilliamR", "williamr@apstay.net", "107", "William Robinson", "222222222228", "MALE", "0112000007", "606 Kiwi Court, Block I"},
                {"Sophiat", "sophiat@apstay.net", "108", "Sophia Turner", "222222222229", "FEMALE", "0112000008", "707 Pine Street, Block J"},
                {"Jamest", "jamest@apstay.net", "109", "James Thompson", "222222222230", "MALE", "0112000009", "808 Palm Avenue, Block K"},
                {"Isabellam", "isabellam@apstay.net", "110", "Isabella Martinez", "222222222231", "FEMALE", "0112000010", "909 Orange Circle, Block L"},
                {"Benjaminm", "benjaminm@apstay.net", "112", "Benjamin Moore", "222222222232", "MALE", "0112000011", "1001 Berry Lane, Block M"},
                {"Miam", "miam@apstay.net", "113", "Mia Jackson", "222222222233", "FEMALE", "0112000012", "1102 Lime Road, Block N"},
                {"Jacobm", "jacobm@apstay.net", "114", "Jacob Martin", "222222222234", "MALE", "0112000013", "1203 Peach Avenue, Block O"},
                {"Charlottel", "charlottel@apstay.net", "115", "Charlotte Lee", "222222222235", "FEMALE", "0112000014", "1304 Plum Street, Block P"},
                {"Ethanh", "ethanh@apstay.net", "116", "Ethan Harris", "222222222236", "MALE", "0112000015", "1405 Apricot Drive, Block Q"},
                {"Ameliac", "ameliac@apstay.net", "117", "Amelia Clark", "222222222237", "FEMALE", "0112000016", "1506 Nectarine Court, Block R"},
                {"Danlewis", "danlewis@apstay.net", "118", "Daniel Lewis", "222222222238", "MALE", "0112000017", "1607 Melon Blvd, Block S"},
                {"Harperw", "harperw@apstay.net", "119", "Harper Walker", "222222222239", "FEMALE", "0112000018", "1708 Fig Avenue, Block T"}
            };
            
            for (String[] userData : users) {
                var existingUser = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                                     .setParameter("email", userData[1])
                                     .getResultList();
                
                if (!existingUser.isEmpty()) {
                    continue;
                }
                
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
                user.setUserProfile(profile);
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