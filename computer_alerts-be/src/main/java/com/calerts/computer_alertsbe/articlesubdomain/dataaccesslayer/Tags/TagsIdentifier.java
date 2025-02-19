package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Tags;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class TagsIdentifier {


    private String tagId;

    public TagsIdentifier() {
        this.tagId = UUID.randomUUID().toString();
    }

    public TagsIdentifier(String tagId) {
        this.tagId = tagId;
    }

}
