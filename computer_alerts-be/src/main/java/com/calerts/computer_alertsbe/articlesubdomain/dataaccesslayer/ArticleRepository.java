package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
        Mono<Article> findArticleByArticleIdentifier_ArticleId(String articleId);

        Flux<Article> findAllArticleByTags(String tagName);

        Flux<Article> findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(String title, String body);
}
