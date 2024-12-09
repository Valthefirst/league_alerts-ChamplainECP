package com.calerts.computer_alertsbe.authorsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.authorsubdomain.datalayer.ArticleList;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorResponseModel {

    private String authorId;
    //    private Username username;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String biography;
    private ArticleList articles;
//    private Password password;
}
