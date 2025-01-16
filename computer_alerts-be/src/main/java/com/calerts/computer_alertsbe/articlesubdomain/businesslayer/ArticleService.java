package com.calerts.computer_alertsbe.articlesubdomain.businesslayer;


import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleRequestModel;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleResponseModel;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ArticleService {

    Flux<ArticleResponseModel> getAllArticles();
    Flux<ArticleResponseModel> getAllArticleForSpecificSport(String category);
    Mono<ArticleResponseModel> getArticleByArticleId(String articleId);

    Mono<ArticleResponseModel> editArticle(String articleId, Mono<ArticleRequestModel> articleRequestModelMono);
    Mono<Void> requestCount(String articleId);
    Mono<Void> resetRequestCounts();

    Mono<List<ArticleResponseModel>> searchArticles(String category, String query);

    Mono<ArticleResponseModel> createArticle(Mono<ArticleRequestModel> articleRequestModel);
    Mono<Void> acceptArticle(String articleId);
    Mono<ArticleResponseModel> createArticleDraft(Mono<ArticleRequestModel> articleRequestModel);
//
//    Mono<String> updateArticleImage(String articleId, FilePart file);
//
//    Mono<String> uploadImage(FilePart file);


}
