package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Categories;

import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Document(collection = "categories")
public class Categories {


    @Embedded
    private CategoriesIdentifier categoriesIdentifier;

    private String categoryName;


}
