package com.pczech.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.*;

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


    @NotNull(message = "First name cannot be null")
    @NotEmpty(message = "First name cannot be empty")
    @NotBlank(message = "First name cannot be blank")
    @Length(min = 3)
    private String firstName;

    @NotNull(message = "Second name cannot be null")
    @NotEmpty(message = "Second name cannot be empty")
    @NotBlank(message = "First name cannot be blank")
    @Length(min = 3)
    private String secondName;

    @Transient()
    private String fullName;


    @Email(message = "It's not correct e-mail address")
    private String email;

    private AppUserRole role;

    @JsonIgnore()
    private boolean tokenValidation;

    private boolean adminApproved;

    @JsonIgnore()
    private String token;

    @JsonIgnore()
    @OneToMany(mappedBy = "repairMan", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<MaintenanceTask> maintenanceTasks = new ArrayList<>();

    @JsonIgnore()
    @ManyToMany()
    private Set<Task> tasks = new LinkedHashSet<>();

    public AppUser() {
        this.username = "";
        this.password = "";
        this.role = AppUserRole.USER;
        this.token = UUID.randomUUID().toString();
    }

    public void generateToken() {
        this.token = UUID.randomUUID().toString();
    }

    @PreRemove()
    public void preRemove() {
        maintenanceTasks.forEach(x -> x.setRepairMan(null));
        maintenanceTasks = null;
    }

    @PostLoad()
    public void postLoad(){
        fullName = firstName + " " + secondName;
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


    @Override()
    public int hashCode(){
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(username);
        hashCodeBuilder.append(id);
        hashCodeBuilder.append(email);

        return hashCodeBuilder.toHashCode();
    }
}
