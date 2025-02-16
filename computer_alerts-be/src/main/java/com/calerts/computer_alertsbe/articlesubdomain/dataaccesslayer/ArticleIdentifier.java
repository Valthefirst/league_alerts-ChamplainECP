package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class ArticleIdentifier {

    private String articleId;

    public ArticleIdentifier() {
        this.articleId = UUID.randomUUID().toString();
    }

    public ArticleIdentifier(String articleId) {
        this.articleId = articleId;
    }

}
