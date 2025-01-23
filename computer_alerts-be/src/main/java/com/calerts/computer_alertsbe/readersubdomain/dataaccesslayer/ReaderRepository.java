package com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReaderRepository extends ReactiveMongoRepository<Reader, String> {
    Mono<Reader> findReaderByReaderIdentifier_ReaderId(String readerId);

    Mono<Reader> findReaderByAuth0userId(String auth0userId);
}
