package com.calerts.computer_alertsbe.articleservice.businesslayer;


import com.calerts.computer_alertsbe.articleservice.presentationlayer.ArticleResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArticleService {

    Flux<ArticleResponseModel> getAllArticleForSpecificSport(String tagName);
    Mono<ArticleResponseModel> getArticleByArticleId(String articleId);
    Mono<Void> requestCount(String articleId);
    Mono<Void> resetRequestCounts();



}
