package com.calerts.computer_alertsbe.AuthDomain.businessLayer;

import com.calerts.computer_alertsbe.AuthDomain.presentationLayer.UserRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    private final RestTemplate restTemplate = new RestTemplate();


    @Value("${auth0.domain}")
    private String AUTH0_DOMAIN;

    @Value("${auth0.clientId}")
    private String CLIENT_ID;

    @Value("${auth0.clientSecret}")
    private String CLIENT_SECRET;

    @Value("${auth0.audience}")
    private String AUDIENCE;

    // Fetch Management API token
    public String getManagementApiToken() {
    String url = "https://dev-im24qkb6l7t2yhha.auth0.com/oauth/token";

    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("client_id", CLIENT_ID);
    requestBody.put("client_secret", CLIENT_SECRET);
    requestBody.put("audience", "https://dev-im24qkb6l7t2yhha.auth0.com/api/v2/");
    requestBody.put("grant_type", "client_credentials");

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

    if (response.getStatusCode() == HttpStatus.OK) {
        return response.getBody().get("access_token").toString();
    } else {
        throw new RuntimeException("Failed to fetch Management API token: " + response.getBody());
    }
}

// Create User
public Map<String, Object> createUser(UserRequestDTO request) {
    String token = getManagementApiToken();
    String url = "https://dev-im24qkb6l7t2yhha.auth0.com/api/v2/users";

    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("email", request.getEmail());
    requestBody.put("password", request.getPassword());
    requestBody.put("connection", "Username-Password-Authentication");
    requestBody.put("name", request.getName());

    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(token);
    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

    ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

    if (response.getStatusCode() == HttpStatus.CREATED) {
        return response.getBody();
    } else {
        throw new RuntimeException("Failed to create user: " + response.getBody());
    }
    }
}
