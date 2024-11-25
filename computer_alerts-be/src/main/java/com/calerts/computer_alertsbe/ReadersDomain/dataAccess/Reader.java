package com.calerts.computer_alertsbe.ReadersDomain.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reader {

    @Id
    private String id;

    private String readerId;

    private String firstName;
    private String lastName;

}
