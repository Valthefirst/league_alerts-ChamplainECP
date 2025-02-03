package com.calerts.computer_alertsbe.reprotsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.reprotsubdomain.businessslayer.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/latest")
    public Mono<ReportResponseModel> getLatestReport() {
        return reportService.createReport();
    }
}
