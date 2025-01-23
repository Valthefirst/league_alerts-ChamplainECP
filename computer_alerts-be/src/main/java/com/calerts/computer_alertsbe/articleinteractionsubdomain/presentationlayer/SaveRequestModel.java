package com.calerts.computer_alertsbe.articleinteractionsubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveRequestModel {

    private String articleId;
    private String readerId;
}
