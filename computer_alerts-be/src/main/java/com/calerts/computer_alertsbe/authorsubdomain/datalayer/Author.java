package com.calerts.computer_alertsbe.authorsubdomain.datalayer;

import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorRequestDTO;
import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorResponseModelAuth;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "authors")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    private String id;

    @Embedded
    private AuthorIdentifier authorIdentifier;

    private String auth0UserId;  // Add this field to store Auth0 user ID
    private String emailAddress;
    private String firstName;
    private String lastName;
    private Biography biography;

    @Embedded
    private ArticleList articles;
}


//public Mono<AuthorResponseModelAuth> createAuthor(AuthorRequestDTO request) {
//    String url = "https://" + AUTH0_DOMAIN + "/api/v2/users";
//    String managementApiToken = getManagementApiToken();
//
//    if (managementApiToken == null || managementApiToken.isEmpty()) {
//        return Mono.error(new RuntimeException("Management API token is missing or invalid"));
//    }
//
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_JSON);
//    headers.set("Authorization", "Bearer " + managementApiToken);
//
//    Map<String, Object> requestBody = new HashMap<>();
//    requestBody.put("email", request.getEmailAddress());
//    requestBody.put("password", request.getPassword());
//    requestBody.put("connection", "Username-Password-Authentication");
//
//    try {
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
//
//        if (response.getStatusCode() == HttpStatus.CREATED) {
//            Map<String, Object> responseBody = response.getBody();
//            String auth0UserId = (String) responseBody.get("user_id");
//
//
//
//
//            Author author = Author.builder()
//                    .authorIdentifier(new AuthorIdentifier())
//                    .emailAddress(request.getEmailAddress())
//                    .firstName(request.getFirstName())
//                    .lastName(request.getLastName())
//                    .biography(new Biography())
//                    .articles(new ArticleList())
//                    .auth0UserId(auth0UserId)
//                    .build();
//
//            return authorRepository.save(author)
//                    .map(savedAuthor -> new AuthorResponseModelAuth(
//                            savedAuthor.getId(),
//                            savedAuthor.getEmailAddress(),
//                            savedAuthor.getFirstName(),
//                            savedAuthor.getLastName(),
//                            auth0UserId
//                    ));
//        } else {
//            return Mono.error(new RuntimeException("Failed to create user in Auth0: " + response.getBody()));
//        }
//    } catch (RestClientException e) {
//        return Mono.error(new RuntimeException("Error communicating with Auth0: " + e.getMessage(), e));
//    }
//}
