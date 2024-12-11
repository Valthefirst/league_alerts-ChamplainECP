package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Like;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface LikeService {

    Mono<Like> likeArticle(ArticleIdentifier articleIdentifier, String readerId);

    Flux<Like> getLikesByArticle(ArticleIdentifier articleIdentifier);

    Flux<Like> getLikesByReader(String readerId);

    Mono<Like> getLikeByIdentifier(String likeId);
}
