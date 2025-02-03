package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Tags;

import jakarta.persistence.Embedded;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Document(collection = "tags")
public class Tags {

    @Embedded
    private TagsIdentifier tagsIdentifier;

    private String tagName;


}
