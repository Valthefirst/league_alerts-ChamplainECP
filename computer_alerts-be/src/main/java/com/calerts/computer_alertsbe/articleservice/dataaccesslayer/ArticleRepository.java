package com.calerts.computer_alertsbe.articleservice.dataaccesslayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
        Mono<Article> findArticleByArticleId(String articleId);

}
