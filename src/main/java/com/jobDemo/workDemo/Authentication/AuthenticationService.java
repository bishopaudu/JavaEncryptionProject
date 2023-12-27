package com.jobDemo.workDemo.Authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationRepository authenticationRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public AuthenticationService(AuthenticationRepository authenticationRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationRepository = authenticationRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public User registerUser(User user){
        String password = user.getPassword();
        String hashPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword((hashPassword));
        return authenticationRepository.save(user);

    }

    public User getUserByEmail(String email){
        return (User) authenticationRepository.findByEmail(email).orElse(null);
    }
}


