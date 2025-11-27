package com.luerde.luerdegeekshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Serviço para enviar e-mails.
 * A anotação @Service indica que esta classe é um serviço do Spring.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Envia um e-mail simples.
     * @param to O destinatário do e-mail.
     * @param subject O assunto do e-mail.
     * @param body O corpo do e-mail.
     */
    public void sendEmail(String to, String subject, String body) {
        // Cria uma mensagem de e-mail simples.
        SimpleMailMessage message = new SimpleMailMessage();
        // Define o destinatário.
        message.setTo(to);
        // Define o assunto.
        message.setSubject(subject);
        // Define o corpo do e-mail.
        message.setText(body);
        // Envia o e-mail.
        mailSender.send(message);
    }
}
