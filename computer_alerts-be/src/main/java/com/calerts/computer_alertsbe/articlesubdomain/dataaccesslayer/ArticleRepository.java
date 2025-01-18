package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer;


import com.calerts.computer_alertsbe.authorsubdomain.datalayer.AuthorIdentifier;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
        Mono<Article> findArticleByArticleIdentifier_ArticleId(String articleId);


        Flux<Article> findAllArticleByCategory(String category);



        Flux<Article> findByCategoryContainingAndTitleContainingIgnoreCase(
                String category,
                String title
        );

        Flux<Article> findByTitleContainingIgnoreCaseOrBodyContainingIgnoreCase(String title, String body);




}
