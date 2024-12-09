package com.calerts.computer_alertsbe.authorsubdomain.datalayer;

import com.calerts.computer_alertsbe.articleservice.dataaccesslayer.ArticleIdentifier;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ArticleList {

    private List<ArticleIdentifier> articleList;

    public ArticleList(List<ArticleIdentifier> articleList) {
        this.articleList = articleList;
    }
}
