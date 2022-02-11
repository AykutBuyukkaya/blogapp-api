package com.buyukkaya.blogappapi.security.service;

import com.buyukkaya.blogappapi.user.model.request.LoginRequest;
import com.buyukkaya.blogappapi.user.model.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest loginRequest);

}
