package com.calerts.computer_alertsbe.articlesubdomain.businesslayer;


import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleRequestModel;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ArticleService {

    Flux<ArticleResponseModel> getAllArticleForSpecificSport(String tagName);
    Mono<ArticleResponseModel> getArticleByArticleId(String articleId);
    Mono<ArticleResponseModel> createArticle(Mono<ArticleRequestModel> articleRequestModel);



}
