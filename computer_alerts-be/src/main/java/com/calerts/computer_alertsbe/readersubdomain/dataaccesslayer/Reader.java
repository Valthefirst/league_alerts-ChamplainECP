package com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer;

import com.calerts.computer_alertsbe.authorsubdomain.datalayer.AuthorIdentifier;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "readers")
@NoArgsConstructor
@Builder
public class Reader {
    @Id
    private String id;
    @Embedded
    private ReaderIdentifier readerIdentifier;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private String address;
    private String auth0userId;
    private AccountStatus accountStatus;

}
