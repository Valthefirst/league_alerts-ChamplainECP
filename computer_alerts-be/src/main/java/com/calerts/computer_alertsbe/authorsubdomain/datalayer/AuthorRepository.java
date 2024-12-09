package com.calerts.computer_alertsbe.authorsubdomain.datalayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Mono<Author> findAuthorByAuthorIdentifier_AuthorId(String authorId);
}
