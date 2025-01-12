package com.calerts.computer_alertsbe.articleinteractionsubdomain.businesslayer;

import com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer.Share;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import reactor.core.publisher.Mono;

public interface ShareService {

    Mono<Share> shareArticle(ArticleIdentifier articleIdentifier, String readerId);
}
