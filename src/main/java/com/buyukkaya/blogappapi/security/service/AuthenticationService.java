package com.buyukkaya.blogappapi.security.service;

import com.buyukkaya.blogappapi.common.model.response.ApiResponse;
import com.buyukkaya.blogappapi.user.model.request.LoginRequest;

import javax.naming.NoPermissionException;

public interface AuthenticationService {

    ApiResponse login(LoginRequest loginRequest);

    boolean checkUserAuthentication(String username);

    void isUserAdmin() throws NoPermissionException;

}

