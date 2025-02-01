package com.calerts.computer_alertsbe.articlesubdomain.subscription;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface SubscriptionRepository extends ReactiveMongoRepository<Subscription, String> {

    // Find all subscriptions by category
    Flux<Subscription> findByCategory(String category);

    // Find all subscriptions by user email
    Flux<Subscription> findByUserEmail(String userEmail);

    // Check if a user is already subscribed to a category
    Mono<Boolean> existsByUserEmailAndCategory(String userEmail, String category);

    // Delete a subscription by user email and category
    Mono<Void> deleteByUserEmailAndCategory(String userEmail, String category);
}



