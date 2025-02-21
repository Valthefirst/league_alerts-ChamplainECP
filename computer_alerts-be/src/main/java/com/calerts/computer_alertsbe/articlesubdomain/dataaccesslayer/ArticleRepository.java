package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
        Mono<Article> findArticleByArticleIdentifier_ArticleId(String articleId);



        @Query("{ 'category.categoryName': ?0 }")
        Flux<Article> findAllArticleByCategory(String category);

        Flux<Article> findByCategory_CategoryNameAndTitleContainingIgnoreCase(
                String category,
                String title
        );

        Flux<Article> findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(String title, String body);




}
