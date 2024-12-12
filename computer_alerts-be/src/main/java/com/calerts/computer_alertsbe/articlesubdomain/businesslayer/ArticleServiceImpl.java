package com.calerts.computer_alertsbe.articlesubdomain.businesslayer;


import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleRepository;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleStatus;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleRequestModel;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleResponseModel;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.utils.exceptions.BadRequestException;
import com.calerts.computer_alertsbe.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Flux<ArticleResponseModel> getAllArticleForSpecificSport(String tagName) {
        return articleRepository.findAllArticleByTags(tagName)
                .map(EntityModelUtil::toArticleResponseModel);

    }

    @Override
    public Mono<ArticleResponseModel> getArticleByArticleId(String articleId) {
        return articleRepository.findArticleByArticleIdentifier_ArticleId(articleId)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("No article with this id was found " + articleId))))
                .map(EntityModelUtil::toArticleResponseModel);
    }

    @Override
    public Mono<ArticleResponseModel> createArticle(Mono<ArticleRequestModel> articleRequestModel) {
        return articleRequestModel
                .filter(article -> article.getTitle() != null && !article.getTitle().isEmpty())
                .switchIfEmpty(Mono.error(new BadRequestException("Article title must not be empty")))
                .filter(article -> article.getWordCount() != 0 && article.getWordCount() > 112)
                .switchIfEmpty(Mono.error(new BadRequestException("Article must have a valid word count")))
                .map(EntityModelUtil::toArticleEntity)
                .map(article -> {
                    article.setArticleStatus(ArticleStatus.ARTICLE_REVIEW);
                    return article;
                })
                .flatMap(articleRepository::save)
                .map(EntityModelUtil::toArticleResponseModel);
    }








}
