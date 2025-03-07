package com.Project.UserAuthentication.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserAuthentication {
    @Id
    String userId;
    String password;
String email;

    public UserAuthentication() {
    }

    public UserAuthentication(String userId, String password,String email) {
        this.userId = userId;
        this.password = password;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAuthentication{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
