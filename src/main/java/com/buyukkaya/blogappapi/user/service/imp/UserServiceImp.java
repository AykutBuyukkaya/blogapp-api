package com.buyukkaya.blogappapi.user.service.imp;

import com.buyukkaya.blogappapi.user.model.entity.UserEntity;
import com.buyukkaya.blogappapi.user.model.request.RegisterRequest;
import com.buyukkaya.blogappapi.user.model.response.RegisterResponse;
import com.buyukkaya.blogappapi.user.repository.UserRepository;
import com.buyukkaya.blogappapi.user.service.RoleService;
import com.buyukkaya.blogappapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        try {

            UserEntity userEntity = new UserEntity(registerRequest.getUsername(), registerRequest.getEmail(),
                    passwordEncoder.encode(registerRequest.getPassword()));
            userEntity.setRole(Collections.singleton(roleService.getRoleByName("USER")));

            userRepository.save(userEntity);

            log.info("User registration successful. Username= {}, Email= {} and with an encoded password."
                    , registerRequest.getUsername(), registerRequest.getEmail());
            return RegisterResponse.builder().message("Registration successful.").build();

        } catch (Exception e) {

            log.error("Error while registration process with error message: {} ", e.getMessage());
            return RegisterResponse.builder().message(e.getMessage()).build();
        }

    }
}
