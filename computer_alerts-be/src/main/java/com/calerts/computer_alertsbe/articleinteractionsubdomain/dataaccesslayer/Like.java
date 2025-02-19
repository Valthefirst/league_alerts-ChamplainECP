package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.ArticleIdentifier;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Like {

    @Id
    private String id;

    @Embedded
    private LikeIdentifier likeIdentifier;

    private ArticleIdentifier articleIdentifier;

    private String readerId;

    private LocalDateTime timestamp;

}

