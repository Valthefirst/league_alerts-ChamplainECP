package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("comments")
public class Comment {

    private String id;

    @Embedded
    private CommentIdentifier commentId;
    private String content;
    private int wordCount;
    private LocalDateTime timestamp;
    @Embedded
    private ArticleIdentifier articleId;
//    @Embedded
    private String readerId;
}
