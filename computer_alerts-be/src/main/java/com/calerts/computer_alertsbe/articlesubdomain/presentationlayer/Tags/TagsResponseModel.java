package com.calerts.computer_alertsbe.articlesubdomain.presentationlayer.Tags;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagsResponseModel {

        private String tagId;
        private String tagName;
}
