package com.calerts.computer_alertsbe.articlesubdomain.subscription;

import com.calerts.computer_alertsbe.emailingsubdomain.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final EmailSenderService emailSenderService;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, EmailSenderService emailSenderService) {
        this.subscriptionRepository = subscriptionRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public void subscribe(String userEmail, String category) {
        subscriptionRepository.existsByUserEmailAndCategory(userEmail, category)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new IllegalStateException("User is already subscribed to this category."));
                    }
                    Subscription subscription = new Subscription(userEmail, category);
                    return subscriptionRepository.save(subscription)
                            .doOnSuccess(sub -> sendSubscriptionConfirmationEmail(userEmail, category));
                })
                .subscribe();
    }


    private void sendSubscriptionConfirmationEmail(String userEmail, String category) {
        String body = "<div style='font-family: Arial, sans-serif; line-height: 1.5; max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px;'>"
                + "<h1 style='color: #a4050a; text-align: center;'>Subscription Confirmation</h1>"
                + "<p style='color: #555;'>Hello,</p>"
                + "<p style='color: #555;'>You've successfully subscribed to updates for <strong>" + category + "</strong> on LeagueAlerts!</p>"
                + "<p style='margin-top: 20px; color: #555;'>Stay updated with the latest news and updates directly in your inbox.</p>"
                + "<p style='margin-top: 20px; color: #555;'>Follow us on social media:</p>"
                + "<div style='text-align: center;'>"
                + "  <a href='https://instagram.com/LeagueAlerts' style='color: #3067f2; text-decoration: none; margin-right: 10px;'>Instagram</a>"
                + "  | <a href='https://x.com/LeagueAlerts' style='color: #3067f2; text-decoration: none; margin-left: 10px;'>X</a>"
                + "</div>"
                + "<p style='margin-top: 20px; color: #555;'>Best regards,<br>LeagueAlerts Team</p>"
                + "</div>";

        emailSenderService.sendEmail(userEmail, "Subscription Confirmation", body).subscribe();
    }



//    @Override
//    public void unsubscribe(String userEmail, String category) {
//        subscriptionRepository.deleteByUserEmailAndCategory(userEmail, category);
//    }

    @Override
    public Mono<Boolean> isAlreadySubscribed(String userEmail, String category) {
        return subscriptionRepository.existsByUserEmailAndCategory(userEmail, category)
                .defaultIfEmpty(false);  // Provide a default value if no result is found
    }


    @Override
    public List<String> getSubscribedCategories(String userEmail) {
        return subscriptionRepository.findByUserEmail(userEmail)
                .toStream()
                .map(Subscription::getCategory)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getSubscribersByCategory(String category) {
        return subscriptionRepository.findByCategory(category)
                .toStream()
                .map(Subscription::getUserEmail)
                .collect(Collectors.toList());
    }
}

