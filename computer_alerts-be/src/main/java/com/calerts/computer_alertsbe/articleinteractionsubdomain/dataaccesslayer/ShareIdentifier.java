package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Getter
@Embeddable
public class ShareIdentifier {

    private String shareId;

    public ShareIdentifier() {
        this.shareId = UUID.randomUUID().toString();
    }
}
