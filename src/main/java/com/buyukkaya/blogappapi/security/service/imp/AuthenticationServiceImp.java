package com.buyukkaya.blogappapi.security.service.imp;

import com.buyukkaya.blogappapi.common.model.response.ApiResponse;
import com.buyukkaya.blogappapi.security.exception.UserNotAuthenticatedException;
import com.buyukkaya.blogappapi.security.jwt.JwtConfiguration;
import com.buyukkaya.blogappapi.security.service.AuthenticationService;
import com.buyukkaya.blogappapi.user.model.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;

    @Override
    public ApiResponse login(LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.info("Granted permission to user {}.", loginRequest.getUsername());

            return ApiResponse.builder()
                    .response(jwtConfiguration.generateToken(authentication))
                    .message("User logged in successfully.")
                    .status(HttpStatus.OK)
                    .build();

        } catch (Exception e) {

            log.error("Error while authenticating user with username: {}.", loginRequest.getUsername());

            return ApiResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();

        }
    }

    @Override
    public boolean checkUserAuthentication(String username) throws UserNotAuthenticatedException {

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals(username)) {
            return true;
        }

        log.error("Username " + username + " does not match with the username on jwt.");
        throw new UserNotAuthenticatedException("Username " + username + " does not match with the username on jwt.");

    }

    @Override
    public void isUserAdmin() throws NoPermissionException {

        if (SecurityContextHolder.getContext().getAuthentication() == null ||
                SecurityContextHolder.getContext().getAuthentication()
                        .getAuthorities().stream()
                        .noneMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            throw new NoPermissionException("Only an admin account can send this request!");
        }
    }


}
