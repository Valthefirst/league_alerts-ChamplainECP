package com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class CommentRequestModel {

    private String content;
    private String articleId;
    private String readerId;
}
