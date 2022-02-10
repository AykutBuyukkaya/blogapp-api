package com.buyukkaya.blogappapi.user.model.request;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;

@Data
@RequiredArgsConstructor
public class RegisterRequest {

    @NonNull
    private String username;

    @NonNull
    private String password;

    @Email
    @NonNull
    private String email;

    
}
