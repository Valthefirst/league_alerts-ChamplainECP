package com.calerts.computer_alertsbe.articlesubdomain.businesslayer;



import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleRepository;
import com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.ArticleResponseModel;
import com.calerts.computer_alertsbe.utils.EntityModelUtil;
import com.calerts.computer_alertsbe.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Void> requestCount(String articleId) {
        return articleRepository.findArticleByArticleIdentifier_ArticleId(articleId)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new NotFoundException("article id was not found: " + articleId))))
                .flatMap(article -> {
                    Integer currentCount = article.getRequestCount() != null ? article.getRequestCount() : 0;
                    article.setRequestCount(currentCount + 1);
                    return articleRepository.save(article).then(); // Save and complete
                });
    }


    @Scheduled(cron = "0 0 0 */30 * *")  
    public Mono<Void> resetRequestCounts() {
        return articleRepository.findAll()
                .flatMap(article -> {
                    article.setRequestCount(0);
                    return articleRepository.save(article);
                })
                .then();
    }
}
