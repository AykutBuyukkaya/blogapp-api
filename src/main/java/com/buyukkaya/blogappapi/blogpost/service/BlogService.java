package com.buyukkaya.blogappapi.blogpost.service;

import com.buyukkaya.blogappapi.blogpost.model.request.CreateBlogRequest;
import com.buyukkaya.blogappapi.common.model.response.ApiResponse;

public interface BlogService {

    ApiResponse createBlog(CreateBlogRequest request);

    ApiResponse getBlogs(int page, int size, String sortDirection, String sortTo);

    ApiResponse getBlogById(Long id);

    ApiResponse getUserBlogs(int page, int size, String sortDirection, String sortTo, String username);

}
