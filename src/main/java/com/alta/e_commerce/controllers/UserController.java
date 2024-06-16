package com.alta.e_commerce.controllers;

import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.UserResponse;
import com.alta.e_commerce.models.WebResponse;
import com.alta.e_commerce.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
        
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public WebResponse<UserResponse> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        UserResponse user = userService.toUserResponse(currentUser);

        return WebResponse.<UserResponse>builder()
            .message("this is your profile:")
            .data(user)
            .build();
    }

    @GetMapping("/users")
    public WebResponse<List<UserResponse>> getAllProfiles() {
        List<User> users = userService.allUsers();

        List<UserResponse> user = userService.toListUserResponse(users);

        return WebResponse.<List<UserResponse>>builder()
            .message("all registered users:")
            .data(user)
            .build();
    }
}