package com.calerts.computer_alertsbe.authsubdomain.presentationlayer;

import com.calerts.computer_alertsbe.authsubdomain.businessLayer.UserService;


import com.calerts.computer_alertsbe.emailingsubdomain.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;


//    @PostMapping("/create")
//    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequest) {
//        try {
//            UserResponseModel userResponse = userService.createUser(userRequest);
//            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(e.getMessage());
//        }
//    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequest) {
        try {
            // Create the user
            UserResponseModel userResponse = userService.createUser(userRequest);

            // Send a welcome email
            try {
                GmailService gmailService = new GmailService();
                String to = userRequest.getEmail();
                String subject = "Welcome to LeagueAlerts!";

                // Styled HTML email body
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
                System.err.println("Failed to send email: " + e.getMessage());
                e.printStackTrace();
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }







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

