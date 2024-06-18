package com.alta.e_commerce.services;

import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.UserResponse;
import com.alta.e_commerce.models.UpdateUserRequest;
import com.alta.e_commerce.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Transactional
    public void delete(String userId){
        User user = userRepository.findById(userId)
            .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user's not found"));

        userRepository.delete(user);
    }

    @Transactional
    public UserResponse update(UpdateUserRequest request){
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "user's not found"));

        user.setNickname(request.getNickname());
        user.setFullname(request.getFullname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setPhoto(request.getPhoto());

        userRepository.save(user);

        return toUserResponse(user);
    }

    @Transactional
    public void savePhoto(User user){
        userRepository.save(user);
    }
}
