package com.calerts.computer_alertsbe.emailingsubdomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public Mono<Void> sendMail(String to, String subject, String body) {
        return Mono.fromRunnable(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("leaguealertsemailingtestecp@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            try {
                mailSender.send(message);
                System.out.println("Email sent to: " + to);
            } catch (Exception e) {
                System.err.println("Failed to send email to: " + to + " due to " + e.getMessage());
            }
        });
    }
}
