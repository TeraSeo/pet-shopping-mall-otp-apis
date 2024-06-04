package com.shoppingmall.otp.service;

public interface UserService {

    String getRole(String email);

    void setVerifiedStatus(String email, boolean isVerified);
}
