package com.calerts.computer_alertsbe.articlesubdomain.businesslayer;


import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleRequestModel;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleResponseModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ArticleService {

    Flux<ArticleResponseModel> getAllArticles();
    Flux<ArticleResponseModel> getAllArticleForSpecificSport(String tagName);
    Mono<ArticleResponseModel> getArticleByArticleId(String articleId);
    Mono<Void> requestCount(String articleId);
    Mono<Void> resetRequestCounts();
    Mono<List<ArticleResponseModel>> searchArticles(String query);
    Mono<ArticleResponseModel> createArticle(Mono<ArticleRequestModel> articleRequestModel);
    Mono<Void> acceptArticle(String articleId);



}
