package com.calerts.computer_alertsbe.ReadersDomain.presentationLayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReaderResponseModel {
    private String readerId;

    private String firstName;
    private String lastName;
}
