package com.alta.e_commerce.models;

public class RegisterUserDto {
    private String nickname;
    private String email;
    private String password;
    //private String fullname; // Mengubah penamaan variabel menjadi fullName dengan huruf besar di awal

    public String getNickname() {
        return nickname;
    }

    public RegisterUserDto setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterUserDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterUserDto setPassword(String password) {
        this.password = password;
        return this;
    }

    // public String getFullname() {
    //     return fullname;
    // }

    // public RegisterUserDto setFullname(String fullname) {
    //     this.fullname = fullname;
    //     return this;
    // }

    @Override
    public String toString() {
        return "RegisterUserDto{" +
                "nickname='" + nickname + '\'' + 
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
