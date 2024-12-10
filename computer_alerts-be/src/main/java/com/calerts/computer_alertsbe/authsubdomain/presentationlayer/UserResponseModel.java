package com.calerts.computer_alertsbe.authsubdomain.presentationlayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponseModel {
    private String email;
    private String password;
}
