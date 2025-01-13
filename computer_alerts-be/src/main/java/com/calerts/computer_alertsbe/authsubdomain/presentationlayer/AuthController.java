package com.calerts.computer_alertsbe.authsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorRequestDTO;
import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorResponseModelAuth;
import com.calerts.computer_alertsbe.authsubdomain.businessLayer.UserService;


import com.calerts.computer_alertsbe.utils.HttpErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class AuthController {


    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequest) {
        try {
            UserResponseModel userResponse = userService.createUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    @PostMapping("/create/Author")
    public Mono<ResponseEntity<AuthorResponseModelAuth>> createAuthor(@RequestBody AuthorRequestDTO authorRequest) {
        return userService.createAuthor(authorRequest)
                .map(authorResponse -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(authorResponse))
                .onErrorResume(e -> {
                    System.out.println("Did Not create user" +e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.BAD_REQUEST).body(null)); // just return nothing or something idk
                });
    }
//    @PostMapping("/addRoles/{auth0UserId}")
//    public Mono<ResponseEntity<String>> addRoles(@PathVariable String auth0UserId) {
//        return userService.addRoles(auth0UserId)
//                .then(Mono.just(ResponseEntity
//                        .status(HttpStatus.NO_CONTENT)
//                        .body("Roles added successfully.")))
//                .onErrorResume(e -> Mono.just(ResponseEntity
//                        .status(HttpStatus.BAD_REQUEST)
//                        .body("Failed to assign roles: " + e.getMessage())));
//    }

        @RequestMapping(value = "/create", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> handlePreflight() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userInfo")
    public ResponseEntity<UserInfoDTO> getUserInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setName(auth.getName());
        userInfo.setRoles(auth.getAuthorities().stream()
                .map(Object::toString)
                .collect(Collectors.toList()));

        return ResponseEntity.ok(userInfo);
    }
}

