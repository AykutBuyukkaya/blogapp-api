package com.buyukkaya.blogappapi.user.service.imp;

import com.buyukkaya.blogappapi.user.model.entity.UserEntity;
import com.buyukkaya.blogappapi.user.model.request.RegisterRequest;
import com.buyukkaya.blogappapi.user.model.response.RegisterResponse;
import com.buyukkaya.blogappapi.user.repository.UserRepository;
import com.buyukkaya.blogappapi.user.service.RoleService;
import com.buyukkaya.blogappapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

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

            //TODO: ADD LOGS HERE.
            return RegisterResponse.builder().message("Registration successful.").build();

        } catch (Exception e) {
            //TODO: ADD LOGS HERE
            return RegisterResponse.builder().message(e.getMessage()).build();
        }

    }
}
