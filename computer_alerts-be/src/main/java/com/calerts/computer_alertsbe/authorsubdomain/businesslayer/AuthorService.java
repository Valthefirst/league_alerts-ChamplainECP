package com.calerts.computer_alertsbe.authorsubdomain.businesslayer;

import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AuthorService {

    Flux<AuthorResponseModel> getAllAuthors();
    Mono<AuthorResponseModel> getAuthor(String authorId);
}
