package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Categories;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesRequestModel {

    private String categoryId;
    private String categoryName;
}
