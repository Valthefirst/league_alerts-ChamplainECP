package com.calerts.computer_alertsbe.articleservice.businesslayer;

import com.calerts.computer_alertsbe.articleservice.presentationlayer.ArticleResponseModel;
import reactor.core.publisher.Mono;

public interface ArticleService {

    Mono<ArticleResponseModel> getArticleByArticleId(String articleId);



}
