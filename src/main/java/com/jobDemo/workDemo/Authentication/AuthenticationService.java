package com.jobDemo.workDemo.Authentication;

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
        user.generateKeys();
        return authenticationRepository.save(user);

    }

    public User getUserByEmail(String email){
        return (User) authenticationRepository.findByEmail(email).orElse(null);
    }
    public User getUserByUsername(String username){
        return (User) authenticationRepository.findByEmail(username).orElse(null);
    }
}


