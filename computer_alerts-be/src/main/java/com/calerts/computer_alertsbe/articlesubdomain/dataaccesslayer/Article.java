package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer;

import com.calerts.computer_alertsbe.authorsubdomain.datalayer.AuthorIdentifier;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;


import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Article {

    @Id
    private String id;

    @Embedded
    private ArticleIdentifier articleIdentifier;

    private String title;
    private String body;
    private Integer wordCount;

    @Field("article_status")
    private ArticleStatus articleStatus;

    private String tags;


    @Embedded
    AuthorIdentifier authorIdentifier;


    private Tags tagsTag;
    private LocalDateTime timePosted;

}
