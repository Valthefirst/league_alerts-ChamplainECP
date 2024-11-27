package com.calerts.computer_alertsbe.AuthDomain.presentationLayer;

import com.calerts.computer_alertsbe.AuthDomain.businessLayer.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000"} ,allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequest) {
        try {
            Map<String, Object> response = userService.createUser(userRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
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

