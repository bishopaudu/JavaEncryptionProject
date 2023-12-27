package com.jobDemo.workDemo.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final   AuthenticationService authenticationService;

  @Autowired
  private AuthenticationManager authenticationManager;
    //AuthenticationRepository authenticationRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User existingUser = authenticationService.getUserByEmail(user.getEmail());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("user already exists");
        } else {
            User registeredUser = authenticationService.registerUser(user);
            if (registeredUser != null) {
              return  ResponseEntity.ok("Registration Successfull");
            } else {
                return ResponseEntity.badRequest().body("Registration Failed");
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password");
        }
    }

    @GetMapping("/registertry")
    public String showRegistrationPage() {
        return "registration";
    }







}
