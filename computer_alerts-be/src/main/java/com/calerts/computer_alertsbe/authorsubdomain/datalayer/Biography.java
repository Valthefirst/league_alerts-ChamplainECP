package com.calerts.computer_alertsbe.authorsubdomain.datalayer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Biography {

    private String biography;
    private int wordCount;

    public Biography() {
        this.biography = "No biography available";
        this.wordCount = 3;
    }
}
