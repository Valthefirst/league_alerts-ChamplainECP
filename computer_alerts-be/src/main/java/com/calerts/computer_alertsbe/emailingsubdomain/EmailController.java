package com.calerts.computer_alertsbe.emailingsubdomain;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EmailController {

    @Autowired
    private EmailSenderService senderService;

    @GetMapping("/api/v1/send-email")
    public Mono<String> sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        return senderService.sendEmail(to, subject, body)
                .then(Mono.just("Email sent successfully"))
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just("Failed to send email: " + e.getMessage());
                });
    }

}