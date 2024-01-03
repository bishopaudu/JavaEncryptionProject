package com.jobDemo.workDemo.Authentication;

import com.jobDemo.workDemo.Authentication.AuthenticationDTO.LoginDTO;
import com.jobDemo.workDemo.Authentication.AuthenticationDTO.SignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:5173/")
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
                HashMap<String,String> resposneBody = new HashMap<>();
                resposneBody.put("registration sucessful","added to the database");
                resposneBody.put("encryption key",user.getEncryptionKey());
                resposneBody.put("decryption key",user.getDecryptionKey());
                resposneBody.put("username",user.getUsername());
              return new  ResponseEntity<>(resposneBody,HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Registration Failed",HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            System.out.println("Received username: " + loginDTO.getUsername());
            System.out.println("Received password: " + loginDTO.getPassword());
            // Authenticate user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
            );

            // If authentication succeeds, return a success message
            return ResponseEntity.ok("Login successful");

        } catch (AuthenticationException e) {
            // If authentication fails, return an error message
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password");
        }
    }


    @GetMapping("/registertry")
    public ResponseEntity<?> showRegistrationPage() {
        return new ResponseEntity<>("testing Authentication controllers",HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllEntities() {
        List<User> entities = authenticationService.getAllEntities();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

}
