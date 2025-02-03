package com.calerts.computer_alertsbe.reprotsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.reprotsubdomain.dataaccesslayer.TopArticle;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ReportResponseModel {

    private String reportIdentifier;
    private LocalDateTime timestamp;
//    private List<String> topAuthors;
    private List<TopArticle> topArticles;
}
