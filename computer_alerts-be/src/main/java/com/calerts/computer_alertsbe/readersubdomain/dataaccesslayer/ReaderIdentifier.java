package com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class ReaderIdentifier {

    private String readerId;

    public ReaderIdentifier() {
        this.readerId = UUID.randomUUID().toString();
    }

    public ReaderIdentifier(String readerId) {
        this.readerId = readerId;
    }

}
