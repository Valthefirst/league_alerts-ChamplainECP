package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleStatus;
import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Tags;
import com.calerts.computer_alertsbe.authorsubdomain.datalayer.AuthorIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseModel {

    private String articleId;

    private String title;
    private String body;
    private int wordCount;

    private ArticleStatus articleStatus;

    private String tags;
    private Tags tagsTag;
    private LocalDateTime timePosted;
    private AuthorIdentifier authorIdentifier;

    private int likeCount;

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
    private Integer requestCount;

    private String photoUrl;
}
