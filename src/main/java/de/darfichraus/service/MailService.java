package de.darfichraus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    void sendCredentialsMail(final String email, final String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Ihr Zugang für DarfIchRaus.de");
        message.setFrom("DarfIchRaus Admin-Team <siteadmin@darfichraus.de>");
        message.setText(MessageFormat.format("Ihr Passwort lautet: {0}. Bitte ändern Sie Ihr Passwort in der WebApp", password));
        mailSender.send(message);
    }


}
