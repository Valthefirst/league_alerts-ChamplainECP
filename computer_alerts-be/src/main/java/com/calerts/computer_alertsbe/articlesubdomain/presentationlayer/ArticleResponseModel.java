package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleStatus;
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
    private LocalDateTime timePosted;
    private Integer requestCount;
}
