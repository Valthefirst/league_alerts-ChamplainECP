package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ShareRepository extends ReactiveMongoRepository<Share, String> {

    Flux<Share> findByArticleIdentifier(ArticleIdentifier articleIdentifier);
}
