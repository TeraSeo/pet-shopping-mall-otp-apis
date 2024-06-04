package com.shoppingmall.otp.controller;

import com.shoppingmall.otp.entity.Otp;
import com.shoppingmall.otp.service.OtpService;
import com.shoppingmall.otp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;
    private final UserService userService;
    private final Logger LOGGER =  LoggerFactory.getLogger(OtpController.class);

    @Autowired
    public OtpController(OtpService otpService, UserService userService) {
        this.otpService = otpService;
        this.userService = userService;
    }

    @PostMapping("/send")
    public ResponseEntity sendOtp(@RequestBody Otp otp) throws NoSuchAlgorithmException {
        String email = otp.getEmail();
        LOGGER.debug("send otp to " + email);
        if (email != null) {
            String code = otpService.generateOtp(email);
            otpService.saveOtp(email, code);
            otpService.sendOtp(email, code);
        }
        return ResponseEntity.ok("");
    }

    @GetMapping("/checkOtp")
    public ResponseEntity<Boolean> checkOtp(@RequestHeader String email) {
        userService.setVerifiedStatus(email, true);
        return ResponseEntity.ok(true); // check through otp authentication filter
    }

}
