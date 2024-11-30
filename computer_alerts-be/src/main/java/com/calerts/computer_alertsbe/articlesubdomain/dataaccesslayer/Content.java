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
    private Integer wordCount;
}
