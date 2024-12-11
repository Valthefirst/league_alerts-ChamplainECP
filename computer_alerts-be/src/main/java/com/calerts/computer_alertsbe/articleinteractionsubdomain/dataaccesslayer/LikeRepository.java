package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LikeRepository extends ReactiveMongoRepository<Like, String> {

    Flux<Like> findByArticleIdentifier(ArticleIdentifier articleIdentifier);

    Flux<Like> findByReaderId(String readerId);

    Mono<Like> findByArticleIdentifierAndReaderId(ArticleIdentifier articleIdentifier, String readerId);

    Mono<Like> findByLikeIdentifier_LikeId(String likeId);
}

