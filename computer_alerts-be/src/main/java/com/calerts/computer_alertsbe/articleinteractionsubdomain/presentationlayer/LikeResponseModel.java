package com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeResponseModel {

    private String likeId;
    private String articleId;
    private String readerId;
    private LocalDateTime timestamp;



}

