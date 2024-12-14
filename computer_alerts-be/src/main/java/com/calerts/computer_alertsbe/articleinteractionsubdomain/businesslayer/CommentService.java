package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.CommentRequestModel;
import com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer.CommentResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CommentService {

    Flux<CommentResponseModel> getAllComments();
    Mono<CommentResponseModel> addComment(Mono<CommentRequestModel> commentRequestModel);
}
