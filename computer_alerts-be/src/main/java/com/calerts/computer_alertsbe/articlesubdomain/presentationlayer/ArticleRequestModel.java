package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleStatus;
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
public class ArticleRequestModel {

    private String articleId;
    private String title;
    private String body;
    private int wordCount;
    private ArticleStatus articleStatus;
    private String category;
    private String tagsTag;
    private LocalDateTime timePosted;
    private String photoUrl;
    private AuthorIdentifier authorIdentifier;
    private String articleDescription;

}
