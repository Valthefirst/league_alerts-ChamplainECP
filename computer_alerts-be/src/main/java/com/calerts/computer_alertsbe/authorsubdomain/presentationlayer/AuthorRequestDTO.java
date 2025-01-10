package com.calerts.computer_alertsbe.authorsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.authorsubdomain.datalayer.Biography;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorRequestDTO {
    private String emailAddress;
    private String password;
    private String firstName;
    private String lastName;
    private Biography biography;
}
