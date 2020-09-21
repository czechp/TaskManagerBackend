package com.pczech.taskmanager.service;

import com.pczech.taskmanager.domain.AppUser;
import com.pczech.taskmanager.domain.AppUserRole;
import com.pczech.taskmanager.exception.AlreadyExistsException;
import com.pczech.taskmanager.exception.BadDataException;
import com.pczech.taskmanager.exception.NotFoundException;
import com.pczech.taskmanager.exception.UnauthorizedException;
import com.pczech.taskmanager.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service()
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService emailSenderService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    //public method section
    @Autowired()
    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, EmailSenderService emailSenderService,
                              AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    @CacheEvict(value = "users", allEntries = true, condition = "#result != null")
    public AppUser register(AppUser appUser, Errors errors, ServletRequest servletRequest) {
        if (!errors.hasErrors()) {
            if (!appUserRepository.existsByUsernameOrEmail(appUser.getUsername(), appUser.getEmail())) {
                appUser.generateToken();
                appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
                AppUser result = appUserRepository.save(appUser);
                result.setPassword("");
                emailSenderService.sendVerificationToken(appUser.getToken(), appUser.getEmail(), servletRequest);
                return result;
            } else {
                throw new AlreadyExistsException("username --- " + appUser.getUsername() + " or email --- " + appUser.getEmail());
            }
        } else
            throw new BadDataException(appUser.getUsername());

    }

    @Override
    public String activateUserByToken(String token) {
        AppUser appUser = appUserRepository.findByToken(token).orElseThrow(() -> new NotFoundException("token --- " + token));
        appUser.setTokenValidation(true);
        appUserRepository.save(appUser);
        return "User activated";
    }

    @Override
    public String login(AppUser appUser) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));
        } catch (Exception e) {
            throw new UnauthorizedException("username --- " + appUser.getUsername());
        }
        return jwtTokenService.generateToken(appUser.getUsername());
    }


    @Override
    @CacheEvict(value = "users", allEntries = true, condition = "#result != true")
    public AppUser activateUserByAdmin(long id, String status) {
        activateUserStatusCorrect(status);
        AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new NotFoundException("user id --- " + id));
        appUser.setAdminApproved(status.equals("activate"));
        AppUser result = appUserRepository.save(appUser);
        result.setPassword("");
        return result;
    }

    @Override
    public String getRoleForUser(AppUser appUser) {
        return appUserRepository.findByUsername(appUser.getUsername())
                .orElseThrow(() -> new NotFoundException("username --- " + appUser.getUsername()))
                .getRole().toString();
    }

    @Override
    public boolean existsByUsername(String username) {
        return appUserRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return appUserRepository.existsByEmail(email);
    }

    @Override
    @CacheEvict(value = "users", allEntries = true)
    public void deleteUserById(long id) {
        if (appUserRepository.existsById(id)) {
            appUserRepository.deleteById(id);
        } else {
            throw new NotFoundException("appUser id: " + id);
        }
    }

    @Override
    @Cacheable(value = "users")
    public List<AppUser> findAll() {
        List<AppUser> users = appUserRepository.findAll();
        users.forEach(x -> x.setPassword(""));
        return users;
    }

    @Override
    public List<HashMap<String, String>> findAllUserStatus() {
        List<HashMap<String, String>> result = new ArrayList<>();
        Arrays.stream(AppUserRole.values()).forEach(x -> {
            HashMap<String, String> role = new HashMap<>();
            role.put("role", x.toString());
            result.add(role);
        });
        return result;
    }

    @Override
    @Transactional()
    @CacheEvict(value = "users", allEntries = true, condition = "#result != null")
    public AppUser modifyRole(long id, String role) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("appUser id --- " + id));
        AppUserRole appUserRole = AppUserRole.getRole(role)
                .orElseThrow(() -> new NotFoundException("role --- " + role));
        appUser.setRole(appUserRole);
        return appUser;
    }

    //private method section
    private void activateUserStatusCorrect(String status) {
        if (!status.equals("activate") && !status.equals("deactivate"))
            throw new BadDataException("Incorrect status value");
    }
}
