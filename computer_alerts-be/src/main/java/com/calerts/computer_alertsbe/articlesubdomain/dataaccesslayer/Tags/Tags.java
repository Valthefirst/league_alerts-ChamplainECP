package com.calerts.computer_alertsbe.articlesubdomain.dataaccesslayer.Tags;

import jakarta.persistence.Embedded;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Tags {

    @Embedded
    private TagsIdentifier tagsIdentifier;

    private String tagName;


}
