package com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentResponseModel {

    private String commentId;
    private String content;
    private int wordCount;
    private LocalDateTime timestamp;
    private String articleId;
    private String readerId;
}
