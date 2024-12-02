package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
public class ArticleIdentifier {

    private String articleId;

    public ArticleIdentifier() {
        this.articleId = UUID.randomUUID().toString();
    }

    public String getArticleId() {
        return articleId;
    }

}
