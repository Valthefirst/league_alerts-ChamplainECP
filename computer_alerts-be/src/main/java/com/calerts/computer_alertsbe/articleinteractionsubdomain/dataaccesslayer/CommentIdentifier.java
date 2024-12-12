package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CommentIdentifier {

    private String commentId;

    public CommentIdentifier() {
        this.commentId = UUID.randomUUID().toString();
    }
}
