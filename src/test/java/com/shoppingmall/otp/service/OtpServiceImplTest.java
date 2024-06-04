package com.shoppingmall.otp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OtpServiceImplTest {

    @Autowired
    private OtpService otpService;

    @Test
    void sendOtp() {
        otpService.sendOtp("seotj0413@gmail.com", "1772");
    }
}