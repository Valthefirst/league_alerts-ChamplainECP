package com.calerts.computer_alertsbe.articlesubdomain.subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam String email, @RequestParam String category) {
        try {
            subscriptionService.subscribe(email, category);
            return ResponseEntity.ok("Subscribed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



//    @DeleteMapping("/unsubscribe")
//    public ResponseEntity<String> unsubscribe(@RequestParam String email, @RequestParam String category) {
//        subscriptionService.unsubscribe(email, category);
//        return ResponseEntity.ok("Unsubscribed successfully.");
//    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getSubscribedCategories(@RequestParam String email) {
        return ResponseEntity.ok(subscriptionService.getSubscribedCategories(email));
    }
}
