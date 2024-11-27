package com.calerts.computer_alertsbe.authservice.presentationlayer;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000"} ,allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class AuthController {

    @GetMapping("/admin/dashboard")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Admin Dashboard Access Granted");
    }

    @GetMapping("/author/create")
    public ResponseEntity<String> authorCreate() {
        return ResponseEntity.ok("Author Create Access Granted");
    }

    @GetMapping("/reader/content")
    public ResponseEntity<String> readerContent() {
        return ResponseEntity.ok("Reader Content Access Granted");
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

