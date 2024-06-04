package com.shoppingmall.otp.service;

import java.security.NoSuchAlgorithmException;

public interface OtpService {

    String generateOtp(String email) throws NoSuchAlgorithmException;

    void sendOtp(String email, String code);

    void saveOtp(String email, String code);

    boolean checkOtp(String email, String code);
}
