package com.buyukkaya.blogappapi.user.controller;

import com.buyukkaya.blogappapi.common.model.response.ApiResponse;
import com.buyukkaya.blogappapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getUsers(@RequestParam("page") int page, @RequestParam("size") int size,
                                                @RequestParam("sortDir") String sortDir, @RequestParam("sortTo") String sortTo) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortDir, sortTo));
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable("username") String username) {
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }
}
