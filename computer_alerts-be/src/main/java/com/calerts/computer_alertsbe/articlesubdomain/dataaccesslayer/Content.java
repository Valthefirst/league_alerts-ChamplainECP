package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class Content {

    private String title;
    private String body;
//    private Integer wordCount;

    public static int calculateWordCount(String body) {
        if (body == null || body.trim().isEmpty()) {
            return 0;
        }
        return body.trim().split("\\s+").length; // Split by whitespace and count
    }
}
