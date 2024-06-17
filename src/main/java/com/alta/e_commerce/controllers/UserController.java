package com.alta.e_commerce.controllers;

import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.UserResponse;
import com.alta.e_commerce.models.WebResponse;
import com.alta.e_commerce.models.UpdateUserRequest;
import com.alta.e_commerce.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @DeleteMapping(
            path = "/users",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();
        
        userService.delete(genzaiNoShiyousha.getUserId());
        return WebResponse.<String>builder()
                .message("success delete data")
                .build();
    }

    @PutMapping(
        path = "/users",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> update(@RequestBody UpdateUserRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();

        request.setUserId(genzaiNoShiyousha.getUserId());

        UserResponse userResponse = userService.update(request);
        return WebResponse.<UserResponse>builder()
            .message("success update data")
            .data(userResponse)
            .build();
    }
}
