package com.mingles.web.controller;
//Get/Update Profile
//Change Password
import com.mingles.web.dto.user.UserResponse;
import com.mingles.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customer")
public class UserController {
    private final UserService userService;


    @GetMapping
    public ResponseEntity<?> hello(){
        return new ResponseEntity<>("Hello from customer", HttpStatus.OK);
    }

    @PostMapping("/hello")
    public ResponseEntity<?> helloPost(){
        return new ResponseEntity<>("Hello from customer", HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getUser() {
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }

}
