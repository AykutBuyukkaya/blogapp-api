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
                                                @RequestParam("sortDir") String sortDir, @RequestParam("sortTo") String sortTo) {
        return ResponseEntity.ok(blogService.getBlogs(page, size, sortDir, sortTo));
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<ApiResponse> getBlog(@PathVariable("blogId") Long id) {
        return ResponseEntity.ok(blogService.getBlogById(id));
    }

    @GetMapping("/user-blogs/{username}")
    public ResponseEntity<ApiResponse> getUserBlogs(@PathVariable("username") String username,
                                                    @RequestParam("page") int page, @RequestParam("size") int size,
                                                    @RequestParam(value = "sortDir", defaultValue = "DESC") String sortDir, @RequestParam(value = "sortTo", defaultValue = "createdAt") String sortTo) {
        return ResponseEntity.ok(blogService.getUserBlogs(page, size, sortDir, sortTo, username));
    }

    @PatchMapping("/{blogId}")
    public ResponseEntity<ApiResponse> updateBlog(@PathVariable("blogId") Long id,
                                                  @RequestBody @Valid CreateBlogRequest createBlogRequest) {
        return ResponseEntity.ok(blogService.updateBlog(id, createBlogRequest));
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<ApiResponse> deleteBlog(@PathVariable("blogId") Long id) {
        return ResponseEntity.ok(blogService.deleteBlog(id));
    }


}
