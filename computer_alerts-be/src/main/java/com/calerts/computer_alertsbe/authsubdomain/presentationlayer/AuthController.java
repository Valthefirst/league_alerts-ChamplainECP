package com.calerts.computer_alertsbe.authsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorRequestDTO;
import com.calerts.computer_alertsbe.authorsubdomain.presentationlayer.AuthorResponseModelAuth;
import com.calerts.computer_alertsbe.authsubdomain.businessLayer.UserService;


import com.calerts.computer_alertsbe.emailingsubdomain.GmailService;
import com.calerts.computer_alertsbe.utils.HttpErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @PreAuthorize("hasAuthority('admin:articles')")
    public Mono<ResponseEntity<AuthorResponseModelAuth>> createUser(@RequestBody AuthorRequestDTO userRequest) {
        return userService.createReader(userRequest)
                .flatMap(authorResponse -> {
                    // Nested try/catch to replicate the original logic
                    try {
                        // Send the welcome email
                        GmailService gmailService = new GmailService();
                        String to = userRequest.getEmailAddress();
                        String subject = "Welcome to LeagueAlerts!";

                        // Same styled HTML email body from your old code
                        String body = "<div style='font-family: Arial, sans-serif; line-height: 1.5; max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px;'>"
                                + "<h1 style='color: #a4050a; text-align: center;'>Welcome to LeagueAlerts!</h1>"
                                + "<p style='color: #555;'>Hello,</p>"
                                + "<p style='color: #555;'>Thank you for joining <strong>LeagueAlerts</strong>. We're excited to have you on board!</p>"
                                + "<p style='margin-top: 20px; color: #555;'>Follow us on social media:</p>"
                                + "<div style='text-align: center;'>"
                                + "  <a href='https://instagram.com/LeagueAlerts' style='color: #3067f2; text-decoration: none; margin-right: 10px;'>Instagram</a>"
                                + "  | <a href='https://x.com/LeagueAlerts' style='color: #3067f2; text-decoration: none; margin-left: 10px;'>X</a>"
                                + "</div>"
                                + "<p style='margin-top: 20px; color: #555;'>Best regards,<br>LeagueAlerts Team</p>"
                                + "</div>";

                        gmailService.sendMail(to, subject, body);
                        System.out.println("Email sent to: " + to);

                    } catch (Exception e) {
                        // Log email sending failure but do not fail the entire pipeline
                        System.err.println("Failed to send email: " + e.getMessage());
                        e.printStackTrace();
                    }

                    // After email attempt, return the successful creation response
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(authorResponse));
                })
                .onErrorResume(e -> {
                    // If createReader(...) fails
                    System.out.println("Did Not create user: " + e.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(null));
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

