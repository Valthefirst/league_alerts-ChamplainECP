package com.calerts.computer_alertsbe.articleservice.dataaccesslayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ContentRepository extends ReactiveMongoRepository<Content, String> {
}
