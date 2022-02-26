package com.buyukkaya.blogappapi.user.service;

import com.buyukkaya.blogappapi.blogpost.model.entity.Blog;
import com.buyukkaya.blogappapi.common.model.response.ApiResponse;
import com.buyukkaya.blogappapi.user.model.request.RegisterRequest;

public interface UserService {

    ApiResponse registerUser(RegisterRequest registerRequest);

    ApiResponse findUserByUsername(String username);

    void addBlogToUser(String username, Blog blog);

    ApiResponse updateUser(Long id, RegisterRequest registerRequest);

    ApiResponse getAllUsers(int page, int size, String sortDirection, String sortTo);

}
