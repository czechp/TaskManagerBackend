package com.pczech.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Data()
@AllArgsConstructor()
@Builder()
@Entity(name = "users")
public class AppUser implements UserDetails {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "username cannot be empty")
    @NotBlank(message = "username cannot be blank")
    @Length(min = 4)
    private String username;



    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    @NotBlank(message = "password cannot be blank")
    @Length(min = 7)
    private String password;

    @Email(message = "It's not correct e-mail address")
    private String email;

    private AppUserRole role;

    @JsonIgnore()
    private boolean tokenValidation;

    private boolean adminApproved;

    @JsonIgnore()
    private String token;


    public AppUser() {
        this.username = "";
        this.password = "";
        this.role = AppUserRole.USER;
        this.token = UUID.randomUUID().toString();
    }

    public void generateToken() {
        this.token = UUID.randomUUID().toString();
    }

    @Override
    @JsonIgnore()
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.toString()));
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
    @JsonIgnore()
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore()
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore()
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return tokenValidation && adminApproved;
    }
}
