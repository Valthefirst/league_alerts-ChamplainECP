package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import java.util.UUID;

public class ShareIdentifier {

    private String shareId;

    public ShareIdentifier() {
        this.shareId = UUID.randomUUID().toString();
    }
}
