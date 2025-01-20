package com.calerts.computer_alertsbe.authsubdomain.businessLayer;

import com.calerts.computer_alertsbe.authorsubdomain.datalayer.*;
import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorRequestDTO;

import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorResponseModelAuth;
import com.calerts.computer_alertsbe.authsubdomain.presentationlayer.RoleRequest;
import com.calerts.computer_alertsbe.authsubdomain.presentationlayer.UserRequestDTO;
import com.calerts.computer_alertsbe.authsubdomain.presentationlayer.UserResponseModel;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.Reader;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.ReaderIdentifier;
import com.calerts.computer_alertsbe.readersubdomain.dataaccesslayer.ReaderRepository;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


import java.util.*;

@Service
public class UserService {

    private final AuthorRepository authorRepository;
    private final ReaderRepository readerRepository;
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

    public UserService(AuthorRepository authorRepository, WebClient.Builder webClientBuilder, ReaderRepository readerRepository) {
        this.authorRepository = authorRepository;
        this.readerRepository = readerRepository;
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
                    "https://" + "dev-im24qkb6l7t2yhha.ca.auth0.com" + "/oauth/token",
                    request,
                    Map.class
            );

            return (String) response.getBody().get("access_token");
        } catch (Exception e) {
            System.err.println("Failed to retrieve Management API token: " + e.getMessage());
            throw new RuntimeException("Could not obtain Management API token", e);
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
    public Mono<AuthorResponseModelAuth> createReader(AuthorRequestDTO request) {
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




                Reader reader = Reader.builder()
                        .readerIdentifier(new ReaderIdentifier())
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .emailAddress(request.getEmailAddress())
                        .auth0userId(auth0UserId)
                        .build();

                return readerRepository.save(reader)
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


    public Mono<Void> assignRoleToAuthor(@PathVariable String userId, @RequestBody RoleRequest request) {
        return webClient.post()
                .uri("https://"+AUTH0_DOMAIN + "/api/v2/users/" + userId + "/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getManagementApiToken())
                .bodyValue(Map.of("roles", List.of("rol_W1iELc1CHmzBtfE4")))
                .retrieve()
                .bodyToMono(Void.class);
    }
    public Mono<Void> assignRoleToReader(@PathVariable String userId, @RequestBody RoleRequest request) {;
        return webClient.post()
                .uri("https://"+AUTH0_DOMAIN + "/api/v2/users/" + userId + "/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + getManagementApiToken())
                .bodyValue(Map.of("roles", List.of("rol_LOREG4N5742ObYCz")))
                .retrieve()
                .bodyToMono(Void.class);
    }

//    public Mono<Void> assignRoleToUser(String auth0UserId, String manegmenttoken) {
//
//        return webClient.post()
//                .uri("https://" + AUTH0_DOMAIN + "/api/v2/users/" + auth0UserId + "/roles")
//                .headers(headers -> headers.setBearerAuth(manegmenttoken))
//                .bodyValue("{ \"roles\": [ \"rol_W1iELc1CHmzBtfE4\" ] }")
//                .retrieve()
//                .toBodilessEntity()
//                .doOnSuccess(response -> System.out.println(("Role '{}' assigned successfully to User ID: {}" + auth0UserId)))
//                .doOnError(error -> System.out.println(("Failed to assign role '{}' to User ID: {}"  +auth0UserId + error)))
//                .then();
//    }








}

