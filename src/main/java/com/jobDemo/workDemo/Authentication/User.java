package com.jobDemo.workDemo.Authentication;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email",unique = true)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "username",nullable = false,unique = true)
    private String username;
    @Column(name = "encryption_key", nullable = false, length = 128)
    private String encryptionKey;

    @Column(name = "decryption_key", nullable = false, length = 128)
    private String decryptionKey;

    @PrePersist
    public void generateKeys() {
        this.encryptionKey = generateRandomKey();
        this.decryptionKey = generateRandomKey();
    }
    private String generateRandomKey() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
