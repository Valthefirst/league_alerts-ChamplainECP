package com.calerts.computer_alertsbe.ReadersDomain.dataAccess;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReaderRepository extends ReactiveMongoRepository<Reader, String> {
    Mono<Reader> findReaderByReaderId(String readerId);
}
