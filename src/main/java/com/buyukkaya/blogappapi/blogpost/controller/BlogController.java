package com.buyukkaya.blogappapi.blogpost.controller;

import com.buyukkaya.blogappapi.blogpost.model.request.CreateBlogRequest;
import com.buyukkaya.blogappapi.blogpost.service.BlogService;
import com.buyukkaya.blogappapi.common.model.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<ApiResponse> createBlog(@RequestBody @Valid CreateBlogRequest createBlogRequest) {
        return ResponseEntity.ok(blogService.createBlog(createBlogRequest));
    }


    @GetMapping
    public ResponseEntity<ApiResponse> getBlogs(@RequestParam("page") int page, @RequestParam("size") int size,
                                                @RequestParam("sortOrder") String sortOrder, @RequestParam("sortTo") String sortTo) {
        return ResponseEntity.ok(blogService.getBlogs(page, size, sortOrder, sortTo));
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<ApiResponse> getBlog(@PathVariable("blogId") Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }


}
