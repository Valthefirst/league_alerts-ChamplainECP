package com.calerts.computer_alertsbe.authservice.presentationlayer;

import lombok.Data;

import java.util.List;
@Data
public class UserInfoDTO {
    private String name;
    private List<String> roles;


}
