package com.calerts.computer_alertsbe.authorsubdomain.presentationlayer;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorResponseModelAuth {
    private String authorId;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String auth0UserId;
}
