package com.buyukkaya.blogappapi.user.service;

import com.buyukkaya.blogappapi.user.model.request.RegisterRequest;
import com.buyukkaya.blogappapi.user.model.response.RegisterResponse;

public interface UserService {

    RegisterResponse registerUser(RegisterRequest registerRequest);



}
