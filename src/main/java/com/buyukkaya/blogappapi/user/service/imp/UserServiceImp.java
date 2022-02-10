package com.buyukkaya.blogappapi.user.service.imp;

import com.buyukkaya.blogappapi.user.model.entity.User;
import com.buyukkaya.blogappapi.user.model.request.RegisterRequest;
import com.buyukkaya.blogappapi.user.model.response.RegisterResponse;
import com.buyukkaya.blogappapi.user.repository.UserRepository;
import com.buyukkaya.blogappapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;


    @Override
    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        try {

            userRepository.save(new User(registerRequest.getUsername(), registerRequest.getEmail(),
                    registerRequest.getPassword()));

            //TODO: ADD LOGS HERE.
            return RegisterResponse.builder().message("Registration successful.").build();

        } catch (Exception e) {
            //TODO: ADD LOGS HERE
            return RegisterResponse.builder().message(e.getMessage()).build();
        }

    }
}
