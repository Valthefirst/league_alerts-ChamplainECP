package com.calerts.computer_alertsbe.reprotsubdomain.dataaccesslayer;

import com.calerts.computer_alertsbe.authorsubdomain.datalayer.AuthorIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "reports")
public class Report {

    @Id
    private String id;

    @Indexed(unique = true)
    private ReportIdentifier reportIdentifier;

    private LocalDateTime timestamp;
//    private List<AuthorIdentifier> topAuthors;
    private List<TopArticle> topArticles;
}
