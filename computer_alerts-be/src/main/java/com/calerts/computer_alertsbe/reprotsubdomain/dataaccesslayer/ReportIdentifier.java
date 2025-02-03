package com.calerts.computer_alertsbe.reprotsubdomain.dataaccesslayer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
//@AllArgsConstructor
public class ReportIdentifier {

    private final String reportId;

    public ReportIdentifier() {
        this.reportId = UUID.randomUUID().toString();
    }
}
