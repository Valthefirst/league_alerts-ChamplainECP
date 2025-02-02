package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Categories;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

@Embeddable
@Getter
public class CategoriesIdentifier {

    private String categoryId;

    public CategoriesIdentifier() {
        this.categoryId = UUID.randomUUID().toString();
    }

    public CategoriesIdentifier(String categoryId) {
        this.categoryId = categoryId;
    }
}
