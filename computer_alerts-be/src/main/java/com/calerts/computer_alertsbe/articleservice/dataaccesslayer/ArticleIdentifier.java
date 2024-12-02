package com.calerts.computer_alertsbe.articleservice.dataaccesslayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Getter
@Embeddable
public class ArticleIdentifier {

    private String articleId;

    public ArticleIdentifier() {
        this.articleId = UUID.randomUUID().toString();
    }

}
