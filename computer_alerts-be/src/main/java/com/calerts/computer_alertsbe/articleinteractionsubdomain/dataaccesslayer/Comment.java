package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("comments")
public class Comment {

    @Id
    private String id;

    @Indexed(unique = true)
    private CommentIdentifier commentId;
    private String content;
    private int wordCount;
    private LocalDateTime timestamp;
    private ArticleIdentifier articleId;
    private String readerId;
}
