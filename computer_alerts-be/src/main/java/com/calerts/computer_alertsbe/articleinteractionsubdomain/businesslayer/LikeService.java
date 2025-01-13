package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Like;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Article;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LikeService {


    Flux<Like> getLikesByArticle(ArticleIdentifier articleIdentifier);

    Flux<Like> getLikesByReader(String readerId);

    Mono<Like> getLikeByIdentifier(String likeId);

    Mono<Like> likeArticle(ArticleIdentifier articleIdentifier, String readerId);
    Mono<Void> unlikeArticle(ArticleIdentifier articleIdentifier, String readerId);
}
