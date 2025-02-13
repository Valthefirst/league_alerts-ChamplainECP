package com.calerts.computer_alertsbe.authsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorRequestDTO;
import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorResponseModelAuth;
import com.calerts.computer_alertsbe.authsubdomain.businessLayer.UserService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/create/Reader")
//    @PreAuthorize("hasAuthority('admin:articles')")
    public Mono<ResponseEntity<AuthorResponseModelAuth>> createUser(@RequestBody AuthorRequestDTO userRequest) {
        return userService.createReader(userRequest)
                .map(authorResponse -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(authorResponse))
                .onErrorResume(e -> {
                    System.out.println("Did Not create user" + e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.BAD_REQUEST).body(null)); // just return nothing or something idk
                });
    }



    @PostMapping("/create/Author")
    @PreAuthorize("hasAuthority('admin:articles')")
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
    @PostMapping("/create/{userId}/assign-role/Author")
    @PreAuthorize("hasAuthority('admin:articles')")
    public Mono<Void> assignAuthorRole (@PathVariable String userId, @RequestBody RoleRequest roleRequest) {
        return userService.assignRoleToAuthor(userId, roleRequest);
    }
    @PostMapping("/create/{userId}/assign-role/Reader")
    public Mono<Void> assignReaderRole (@PathVariable String userId, @RequestBody RoleRequest roleRequest) {
        return userService.assignRoleToReader(userId, roleRequest);
    }
    @PostMapping("/create/{userId}/assign-role/Google")
    public Mono<Void> assignReaderRole (@PathVariable String userId) {
        return userService.assignRoleToGoogleAccount(userId);
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

