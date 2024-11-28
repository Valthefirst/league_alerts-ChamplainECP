package com.calerts.computer_alertsbe.articleservice.dataaccesslayer;


import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.lang.annotation.Target;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Article {

    @Id
    private String id;
    private String articleId;

    private String title;
    private String body;
    private int wordCount;

    @Enumerated(EnumType.STRING)
    private ArticleStatus articleStatus;

    private String tags;
    private LocalDateTime timePosted;

}
