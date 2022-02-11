package com.buyukkaya.blogappapi.security.service.imp;

import com.buyukkaya.blogappapi.security.jwt.JwtConfiguration;
import com.buyukkaya.blogappapi.security.service.AuthenticationService;
import com.buyukkaya.blogappapi.user.model.request.LoginRequest;
import com.buyukkaya.blogappapi.user.model.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Granted permission to user {}.", loginRequest.getUsername());

            return LoginResponse.builder()
                    .token(jwtConfiguration.generateToken(authentication))
                    .message("User logged in succesfully.")
                    .build();

        } catch (Exception e) {

            log.error("Error while authenticating user with username: {}.", loginRequest.getUsername());

            return LoginResponse.builder()
                    .token("")
                    .message(e.getMessage())
                    .build();

        }
    }
}
