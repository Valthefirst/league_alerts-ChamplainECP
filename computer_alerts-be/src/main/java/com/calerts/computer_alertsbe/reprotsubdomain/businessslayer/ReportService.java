package com.calerts.computer_alertsbe.reprotsubdomain.businessslayer;

import com.calerts.computer_alertsbe.reprotsubdomain.presentationlayer.ReportResponseModel;
import reactor.core.publisher.Mono;

public interface ReportService {

    Mono<ReportResponseModel> createReport();
}
