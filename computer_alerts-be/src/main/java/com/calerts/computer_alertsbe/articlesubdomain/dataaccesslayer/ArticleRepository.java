package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer;


import com.calerts.computer_alertsbe.authorsubdomain.datalayer.AuthorIdentifier;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {
        Mono<Article> findArticleByArticleIdentifier_ArticleId(String articleId);

        Flux<Article> findAllArticleByTags(String tagName);

        Flux<Article> findByAuthorIdentifier(AuthorIdentifier authorIdentifier);

}
