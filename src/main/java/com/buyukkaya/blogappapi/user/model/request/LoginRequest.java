package com.buyukkaya.blogappapi.user.model.request;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginRequest {

    @NonNull
    private String username;

    @NonNull
    private String password;

}
