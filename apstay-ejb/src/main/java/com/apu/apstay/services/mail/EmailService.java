package com.apu.apstay.services.mail;

import com.apu.apstay.exceptions.EmailException;
import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 *
 * @author alexc
 */
@Stateless
public class EmailService {
    @Resource(name="mail/apstay")
    private Session mailSession;

    public void sendActivationEmail(String to, String activationLink) throws EmailException {
        try {
            var message = new MimeMessage(mailSession);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Activate Your Account");
            message.setText("Click the following link to activate your account: " + activationLink);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(System.out);
            throw new EmailException("Failed to send activation email", e);
        }
    }
}
