package com.apu.apstay.services.mail;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexc
 */
@Stateless
public class EmailService {
    
    private static final Logger logger = Logger.getLogger(EmailService.class.getName());
    
    @Resource(name="mail/apstay")
    private Session mailSession;
    
    public boolean sendPasswordResetEmail(String recipientEmail, String resetLink) {
        try {
            var message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress("noreply@apstay.net"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("APStay - Password Reset Request");
            
            var emailBody = 
                    "<html><body>" +
                    "<h2>APStay Password Reset</h2>" +
                    "<p>We received a request to reset your password. Click the link below to set a new password:</p>" +
                    "<p><a href=\"" + resetLink + "\">Reset Your Password</a></p>" +
                    "<p>If you didn't request this, you can safely ignore this email.</p>" +
                    "<p>The password reset link will expire in 24 hours.</p>" +
                    "<p>Thank you,<br>APStay Team</p>" +
                    "</body></html>";
            
            message.setContent(emailBody, "text/html; charset=utf-8");
            
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Failed to send password reset email", e);
            return false;
        }
    }
}
