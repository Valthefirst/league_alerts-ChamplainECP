package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ArticleIdentifer {

    private String articleId;

    public ArticleIdentifer() {
        this.articleId = UUID.randomUUID().toString();
    }

    public ArticleIdentifer(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return articleId;
    }


}
