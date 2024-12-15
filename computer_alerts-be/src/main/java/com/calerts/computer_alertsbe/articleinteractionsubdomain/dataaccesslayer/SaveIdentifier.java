package com.calerts.computer_alertsbe.articleinteractionsubdomain.dataaccesslayer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class SaveIdentifier {

    private String saveId;

    public SaveIdentifier() {
        this.saveId = UUID.randomUUID().toString();
    }
}
