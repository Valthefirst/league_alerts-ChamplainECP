package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Tags;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Categories.Categories;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface TagsRepository extends ReactiveMongoRepository<Tags, String> {

    Mono<Tags> findTagsByTagName(String tagName);

}
