package com.calerts.computer_alertsbe.reprotsubdomain.dataaccesslayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopArticle {

    private String articleId;
    private int likeCount;
    private int shareCount;
    private int requestCount;
    private int commentCount;
    private int points;
}
