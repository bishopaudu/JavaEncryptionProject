package com.jobDemo.workDemo.Authentication;

import com.jobDemo.workDemo.Authentication.AuthenticationDTO.LoginDTO;
import com.jobDemo.workDemo.Authentication.AuthenticationDTO.SignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/api")
public class AuthenticationController {
  private final AuthenticationService authenticationService;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager,AuthenticationService authenticationService){
      this.authenticationManager = authenticationManager;
      this.authenticationService = authenticationService;
  }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupDTO signupDTO) {
        User existingUser = authenticationService.getUserByEmail(signupDTO.getEmail());
        if (existingUser != null) {
            return new  ResponseEntity<>("user already exists", HttpStatus.BAD_REQUEST);
        } else {
            User user = new User();
            user.setUsername(signupDTO.getUsername());
            user.setEmail(signupDTO.getEmail());
            user.setPassword(signupDTO.getPassword());
            User registeredUser = authenticationService.registerUser(user);
            if (registeredUser != null) {
              return new  ResponseEntity<>("Registration Successfull",HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Registration Failed",HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User login successfully!...", HttpStatus.OK);
    }

    @GetMapping("/registertry")
    public ResponseEntity<?> showRegistrationPage() {
        return new ResponseEntity<>("testing Authentication controllers",HttpStatus.OK);
    }







}
