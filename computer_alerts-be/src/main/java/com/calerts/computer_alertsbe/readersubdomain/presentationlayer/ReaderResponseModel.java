package com.calerts.computer_alertsbe.readersubdomain.presentationlayer;

import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.AccountStatus;
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
    private String address;
    private String emailAddress;
    private String auth0UserId;
    private AccountStatus accountStatus;
}
