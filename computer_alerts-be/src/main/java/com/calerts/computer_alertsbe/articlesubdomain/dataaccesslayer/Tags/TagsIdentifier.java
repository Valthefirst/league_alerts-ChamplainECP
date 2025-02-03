package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Tags;

import com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Categories.CategoriesIdentifier;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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
