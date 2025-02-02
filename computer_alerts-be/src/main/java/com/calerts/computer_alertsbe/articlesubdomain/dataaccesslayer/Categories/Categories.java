package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Categories;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Categories {


    @Embedded
    private CategoriesIdentifier categoriesIdentifier;

    private String categoryName;


}
