package com.alta.e_commerce.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    private String userId;
    private String nickname;
    private String fullname;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String photo;
}
