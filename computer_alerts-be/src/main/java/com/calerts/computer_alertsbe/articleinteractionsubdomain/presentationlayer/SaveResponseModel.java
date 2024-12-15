package com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SaveResponseModel {

    private String saveId;
    private LocalDateTime timestamp;
    private String articleId;
    private String readerId;
}
