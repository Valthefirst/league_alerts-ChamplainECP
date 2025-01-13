package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;


import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Share {

    private String id;

    private ShareIdentifier shareIdentifier;

    private String readerId;

    private ArticleIdentifier articleIdentifier;

    private LocalDateTime timestamp;

    private String shareLink;
}
