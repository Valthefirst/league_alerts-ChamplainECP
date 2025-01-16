package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.*;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ShareServiceImpl implements ShareService{


    private final String SHARE_LINK = "http://localhost:8080/api/v1/articles/";
    private final ShareRepository shareRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ShareServiceImpl(ShareRepository shareRepository , ArticleRepository articleRepository){
        this.shareRepository = shareRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public Mono<Share> shareArticle(ArticleIdentifier articleIdentifier, String readerId) {

        Share share = Share.builder()
                .shareIdentifier(new ShareIdentifier())
                .articleIdentifier(articleIdentifier)
                .readerId(readerId)
                .timestamp(LocalDateTime.now())
                .shareLink(SHARE_LINK + articleIdentifier.getArticleId())
                .build();

        return shareRepository.save(share)
                .flatMap(savedShare -> articleRepository.findArticleByArticleIdentifier_ArticleId(articleIdentifier.getArticleId())
                        .flatMap(article -> {
                            article.incrementShareCount();
                            return articleRepository.save(article);
                        })
                        .thenReturn(savedShare));
    }

    @Override
    public Flux<Share> getSharesByArticle(ArticleIdentifier articleIdentifier) {
        return shareRepository.findByArticleIdentifier(articleIdentifier);
    }

}
