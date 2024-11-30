package com.calerts.computer_alertsbe.articleservice.dataaccesslayer;

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
