package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.*;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ShareServiceImpl implements ShareService{


    private final String SHARE_LINK = "http://localhost:8080/api/v1/articles/";
    private final ShareRepository shareRepository;

    @Autowired
    public ShareServiceImpl(ShareRepository shareRepository) {
        this.shareRepository = shareRepository;
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

        return shareRepository.save(share);
    }

}
