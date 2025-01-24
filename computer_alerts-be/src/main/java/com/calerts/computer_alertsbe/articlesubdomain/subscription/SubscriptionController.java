package com.calerts.computer_alertsbe.articlesubdomain.subscription;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @PostMapping("/subscribe")
    public Mono<ResponseEntity<String>> subscribe(@RequestParam String email, @RequestParam String category) {
        Subscription subscription = Subscription.builder()
                .email(email)
                .category(category)
                .subscriptionDate(LocalDateTime.now())
                .build();

        return subscriptionRepository.save(subscription)
                .thenReturn(ResponseEntity.ok("Successfully subscribed to category: " + category));
    }

    @GetMapping
    public Mono<ResponseEntity<String>> getAllSubscriptions() {
        return subscriptionRepository.findAll()
                .collectList()
                .map(subscriptions -> ResponseEntity.ok(subscriptions.toString()));
    }

    @DeleteMapping("/unsubscribe")
    public Mono<ResponseEntity<String>> unsubscribe(@RequestParam String email, @RequestParam String category) {
        return subscriptionRepository.findByCategory(category)
                .filter(sub -> sub.getEmail().equals(email))
                .flatMap(subscriptionRepository::delete)
                .then(Mono.just(ResponseEntity.ok("Unsubscribed successfully from category: " + category)));
    }
}

