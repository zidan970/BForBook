package com.alta.e_commerce.controllers;

import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.LoginUserDto;
import com.alta.e_commerce.models.RegisterUserDto;
import com.alta.e_commerce.models.UserResponse;
import com.alta.e_commerce.models.WebResponse;
import com.alta.e_commerce.models.LoginResponse;
import com.alta.e_commerce.services.AuthenticationService;
import com.alta.e_commerce.services.UserService;
import com.alta.e_commerce.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public WebResponse<UserResponse> register(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        UserResponse user = userService.toUserResponse(registeredUser);

        return WebResponse.<UserResponse>builder()
            .message("thank you for your registration:")
            .data(user)
            .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}