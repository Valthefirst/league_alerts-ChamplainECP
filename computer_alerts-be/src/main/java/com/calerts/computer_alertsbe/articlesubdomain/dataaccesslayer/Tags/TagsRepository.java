package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Tags;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TagsRepository extends ReactiveMongoRepository<Tags, String> {

    Mono<Tags> findTagsByTagName(String tagName);
    Flux<Tags> findByTagNameContainingIgnoreCase(String tagName);
}
