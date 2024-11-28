package com.calerts.computer_alertsbe.articleservice.presentationlayer;

import com.calerts.computer_alertsbe.articleservice.dataaccesslayer.ArticleStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String tags;
    private LocalDateTime timePosted;
}
