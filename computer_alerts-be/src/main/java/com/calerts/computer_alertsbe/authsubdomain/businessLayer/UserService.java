package com.calerts.computer_alertsbe.authsubdomain.businessLayer;

import com.calerts.computer_alertsbe.authorsubdomain.datalayer.*;
import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorRequestDTO;

import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorResponseModelAuth;
import com.calerts.computer_alertsbe.authsubdomain.presentationlayer.UserRequestDTO;
import com.calerts.computer_alertsbe.authsubdomain.presentationlayer.UserResponseModel;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Service
public class UserService {

    private final AuthorRepository authorRepository;
    private final WebClient webClient;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String AUTHOR_ROLE_ID = "rol_W1iELc1CHmzBtfE4";

    @Value("${auth0.domain}")
    private String AUTH0_DOMAIN;

    @Value("${auth0.clientId}")
    private String CLIENT_ID;

    @Value("${auth0.clientSecret}")
    private String CLIENT_SECRET;

    @Value("${auth0.audience}")
    private String AUDIENCE;

    public UserService(AuthorRepository authorRepository, WebClient.Builder webClientBuilder) {
        this.authorRepository = authorRepository;
        this.webClient = WebClient.builder().build();
        // Initialize Unirest
        Unirest.config()
                .setDefaultHeader("Content-Type", "application/json")
                .setDefaultHeader("Accept", "application/json");
    }

    // Retrieves the Management API token
    private String getManagementApiToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("grant_type", "client_credentials");
            map.add("client_id", CLIENT_ID);
            map.add("client_secret", CLIENT_SECRET);
            map.add("audience", AUDIENCE);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    "https://" + AUTH0_DOMAIN + "/oauth/token",
                    request,
                    Map.class
            );

            return (String) response.getBody().get("access_token");
        } catch (Exception e) {
            System.err.println("Failed to retrieve Management API token: " + e.getMessage());
            throw new RuntimeException("Could not obtain Management API token", e);
        }
    }


    public UserResponseModel createUser(UserRequestDTO request) {
        String url = "https://" + AUTH0_DOMAIN + "/api/v2/users";

        String managementApiToken = getManagementApiToken();
        if (managementApiToken == null || managementApiToken.isEmpty()) {
            throw new RuntimeException("Management API token is missing or invalid");
        }

        // Add headers with Management API token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + managementApiToken);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", request.getEmail());
        requestBody.put("password", request.getPassword());
        requestBody.put("connection", "Username-Password-Authentication");

        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                Map<String, Object> responseBody = response.getBody();
                String userId = (String) responseBody.get("user_id");

                return new UserResponseModel(
                        request.getEmail(),
                        userId // Include user ID in response model
                );
            } else {
                throw new RuntimeException("Failed to create user: " + response.getBody());
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error communicating with Auth0: " + e.getMessage(), e);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////

    public Mono<AuthorResponseModelAuth> createAuthor(AuthorRequestDTO request) {
        String url = "https://" + AUTH0_DOMAIN + "/api/v2/users";
        String managementApiToken = getManagementApiToken();

        if (managementApiToken == null || managementApiToken.isEmpty()) {
            return Mono.error(new RuntimeException("Management API token is missing or invalid"));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + managementApiToken);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", request.getEmailAddress());
        requestBody.put("password", request.getPassword());
        requestBody.put("connection", "Username-Password-Authentication");

        try {
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.CREATED) {
                Map<String, Object> responseBody = response.getBody();
                String auth0UserId = (String) responseBody.get("user_id");

                assignRoleToUser(auth0UserId,managementApiToken);

//                try {
//                    String goodUserId = auth0UserId.replace("|", "%7C");
//                    HttpResponse<String> roleResponse = Unirest.post("https://" + AUTH0_DOMAIN + "/api/v2/users/" + goodUserId + "/roles")
//                            .header("Content-Type", "application/json")
//                            .header("Authorization", "Bearer " + managementApiToken)
//                            .header("Cache-Control", "no-cache")
//                            .body("{ \"roles\":  [ \"rol_W1iELc1CHmzBtfE4\" ] }")
//                            .asString();
//
//                    if (roleResponse.getStatus() != 204) {
//                        return Mono.error(new RuntimeException("Failed to assign author role. Status: " + roleResponse.getStatus()));
//                    }
//            } catch (UnirestException e) {
//                return Mono.error(new RuntimeException("Error assigning author role: " + e.getMessage(), e));
//            }


            Author author = Author.builder()
                        .authorIdentifier(new AuthorIdentifier())
                        .emailAddress(request.getEmailAddress())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .biography(new Biography())
                        .articles(new ArticleList())
                        .auth0UserId(auth0UserId)
                        .build();

                return authorRepository.save(author)
                        .map(savedAuthor -> new AuthorResponseModelAuth(
                                savedAuthor.getId(),
                                savedAuthor.getEmailAddress(),
                                savedAuthor.getFirstName(),
                                savedAuthor.getLastName(),
                                auth0UserId
                        ));
            } else {
                return Mono.error(new RuntimeException("Failed to create user in Auth0: " + response.getBody()));
            }
        } catch (RestClientException e) {
            return Mono.error(new RuntimeException("Error communicating with Auth0: " + e.getMessage(), e));
        }
    }

    public Mono<Void> assignRoleToUser(String auth0UserId, String manegmenttoken) {

        return webClient.post()
                .uri("https://" + AUTH0_DOMAIN + "/api/v2/users/" + auth0UserId + "/roles")
                .headers(headers -> headers.setBearerAuth(manegmenttoken))
                .bodyValue("{ \"roles\": [ \"rol_W1iELc1CHmzBtfE4\" ] }")
                .retrieve()
                .toBodilessEntity()
                .doOnSuccess(response -> System.out.println(("Role '{}' assigned successfully to User ID: {}" + auth0UserId)))
                .doOnError(error -> System.out.println(("Failed to assign role '{}' to User ID: {}"  +auth0UserId + error)))
                .then();
    }
//    public void addRoles(String auth0UserId, String managementToken) {
//
//        String urlCompliantUserId = auth0UserId.replace("|", "%7C");
//        String url = "https://" + AUTH0_DOMAIN + "/api/v2/users/" + urlCompliantUserId + "/roles";
//        String managementApiToken2 = managementToken;
//
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + managementApiToken2);
//
//        // Define roles (this could be dynamic or come from a parameter)
//        Map<String, Object> body = new HashMap<>();
//        body.put("roles", new String[] { "rol_W1iELc1CHmzBtfE4" });  // Replace ROLE_ID with the actual role ID
//
//            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
//            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
//
//
//    }


//    public Message addRole(AddRole addRole) throws UnirestException, JSONException {
//        String accessToken = getAccessToken();
//        String urlCompliant = addRole.getUserId().replace("|", "%7C");
//
//        HttpResponse<String> response = Unirest.post("https://dev-7k6npylc7qks07rv.us.auth0.com/api/v2/users/" + urlCompliant + "/roles")
//                .header("content-type", "application/json")
//                .header("authorization", "Bearer " + accessToken)
//                .header("cache-control", "no-cache")
//                .body("{ \"roles\": [ \"rol_ateA49X4oBWvfywq\" ] }")
//                .asString();
//
//        log.info("response: {}", response.getBody());
//
//        if (response.getBody() != null) {
//            return Message.from("Failed to add role for user: " + addRole.getUserId());
//        }
//
//        final var text = "Role added for user: " + addRole.getUserId();
//
//        return Message.from(text);
//    }








}

