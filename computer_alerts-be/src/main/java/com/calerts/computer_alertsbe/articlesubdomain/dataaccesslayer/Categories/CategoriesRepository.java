package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Categories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoriesRepository extends ReactiveMongoRepository<Categories, String> {

    Mono<Categories> findByCategoryName(String categoryName);
}
