package com.calerts.computer_alertsbe.articleservice.dataaccesslayer;


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
    private Content content;
    private ArticleStatus articleStatus;
    private String tags;
    private LocalDateTime timePosted;

}
