package com.calerts.computer_alertsbe.reprotsubdomain.dataaccesslayer;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ReportRepository extends ReactiveMongoRepository<Report, String> {

    Mono<Report> findReportByReportIdentifier_ReportId(String reportId);
}
