package com.calerts.computer_alertsbe.articlesubdomain.subscription;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "subscriptions")
public class Subscription {

    @Id
    private String id;

    @Field("user_email")
    private String email;

    @Field("category")
    private String category;

    private LocalDateTime subscriptionDate;
}

