package com.buyukkaya.blogappapi.user.service.imp;

import com.buyukkaya.blogappapi.blogpost.model.entity.Blog;
import com.buyukkaya.blogappapi.common.model.response.ApiResponse;
import com.buyukkaya.blogappapi.security.service.AuthenticationService;
import com.buyukkaya.blogappapi.user.exception.EMailExistException;
import com.buyukkaya.blogappapi.user.exception.UserNotFoundException;
import com.buyukkaya.blogappapi.user.exception.UsernameExistException;
import com.buyukkaya.blogappapi.user.model.entity.UserEntity;
import com.buyukkaya.blogappapi.user.model.request.RegisterRequest;
import com.buyukkaya.blogappapi.user.repository.UserRepository;
import com.buyukkaya.blogappapi.user.service.RoleService;
import com.buyukkaya.blogappapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final AuthenticationService authenticationService;

    @Override
    public ApiResponse registerUser(RegisterRequest registerRequest) {
        try {

            checkIfEmailOrUsernameExist(registerRequest);

            UserEntity userEntity = new UserEntity(registerRequest.getUsername(), registerRequest.getEmail(),
                    passwordEncoder.encode(registerRequest.getPassword()));


            userEntity.setRole(Collections.singleton(roleService.getRoleByName(registerRequest.getRole())));
            userEntity.setPosts(new ArrayList<>());

            userRepository.save(userEntity);

            log.info("User registration successful. Username= {}, Email= {} and with an encoded password."
                    , registerRequest.getUsername(), registerRequest.getEmail());
            return ApiResponse.builder()
                    .message("Registration successful.")
                    .status(HttpStatus.OK)
                    .build();

        } catch (Exception e) {

            log.warn("Error while registration process with error message: {} ", e.getMessage());
            return createResponseWithException(e.getMessage());

        }

    }

    @Override
    public ApiResponse findUserByUsername(String username) {
        try {
            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .response(userRepository.findByUsername(username).orElseThrow(() ->
                            new UserNotFoundException("User with given username " + username + " is not found!")))
                    .build();

        } catch (Exception e) {
            return createResponseWithException(e.getMessage());
        }
    }

    @Override
    public void addBlogToUser(String username, Blog blog) throws RuntimeException {

        authenticationService.checkUserAuthentication(username);

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " is not found"));

        userEntity.getPosts().add(blog);
        userRepository.save(userEntity);

    }

    @Override
    public ApiResponse updateUser(String username, RegisterRequest registerRequest) {

        try {

            UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User with given username " + username + " is not found."));

            //Only the user or an admin can update user info.
            if (registerRequest.getUsername().equals(user.getUsername())) {
                authenticationService.isUserAdmin();
            }
            checkIfEmailOrUsernameExist(registerRequest);

            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getUsername());

            userRepository.save(user);

            return ApiResponse.builder()
                    .message("User " + registerRequest.getUsername() + " updated successfully")
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return createResponseWithException(e.getMessage());
        }


    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ApiResponse getAllUsers(int page, int size, String sortDirection, String sortTo) {

        try {
            authenticationService.isUserAdmin();
            PageRequest pageRequest = PageRequest.of(page, size,
                    Sort.by(Sort.Direction.fromString(sortDirection), sortTo));

            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .response(userRepository.findAll(pageRequest))
                    .build();
        } catch (Exception e) {
            return createResponseWithException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ApiResponse deleteUser(String username) {

        try {

            userRepository.deleteByUsername(username);

            return ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .message("User " + username + " deleted successfully")
                    .build();


        } catch (Exception e) {
            return createResponseWithException(e.getMessage());
        }


    }


    private void checkIfEmailOrUsernameExist(RegisterRequest registerRequest) throws UsernameExistException, EMailExistException {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            log.warn("User with given e mail {} already exist.", registerRequest.getEmail());
            throw new EMailExistException("User with given e mail " + registerRequest.getEmail() + " already exist.");
        }
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            log.warn("User with username {} already exist.", registerRequest.getUsername());
            throw new UsernameExistException("User with username " + registerRequest.getUsername() + " already exist.");
        }

    }

    private ApiResponse createResponseWithException(String message) {

        log.error(message);

        return ApiResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(message)
                .build();
    }

}
