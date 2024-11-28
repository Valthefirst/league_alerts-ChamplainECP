package com.calerts.computer_alertsbe.articleservice.businesslayer;


import com.calerts.computer_alertsbe.articleservice.dataaccesslayer.ArticleRepository;
import com.calerts.computer_alertsbe.articleservice.presentationlayer.ArticleResponseModel;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository articleRepository;
    @Override
    public Mono<ArticleResponseModel> getArticleByArticleId(String articleId) {
        return articleRepository.findArticleByArticleId(articleId)
                .switchIfEmpty(Mono.error(new NotFoundException("No article with this id was found" + articleId)))
                .map(EntityModelUtil::toArticleResponseModel);
    }
}
