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
        emailSenderService.sendEmail(userEmail, "Subscription Confirmation" + userEmail,
                "You've successfully subscribed to category: " + category).subscribe();
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

