package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SaveRepository extends ReactiveMongoRepository<Save, String> {

    Flux<Save> findSavesByReaderId(String readerId);
    Mono<Save> findSaveBySaveId_SaveId(String saveId);
    Mono<Save> findSaveByArticleId_ArticleIdAndReaderId(String articleId, String readerId);
}
