package com.FelipeMovio.banco.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    // to = para quem estou enviando
    // subject = define o assunto do e-mail
    // message = mensagem
    public void enviarEmail(String to, String subject, String message){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("abcd_test@email.com");
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        emailSender.send(email);
    }

}
