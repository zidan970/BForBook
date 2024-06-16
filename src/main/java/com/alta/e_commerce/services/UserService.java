package com.alta.e_commerce.services;

import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.UserResponse;
import com.alta.e_commerce.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public UserResponse toUserResponse(User user) {
        return new UserResponse(
            user.getUserId(),
            user.getNickname(),
            user.getFullname(),
            user.getEmail(),
            user.getPhone(),
            user.getAddress(),
            user.getPhoto()
        );
    }

    public List<UserResponse> toListUserResponse(List<User> users) {

        return users.stream()
            .map(this::toUserResponse)
            .collect(Collectors.toList());
    }
}
