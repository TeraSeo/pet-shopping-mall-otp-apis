package com.shoppingmall.otp.service;

import com.shoppingmall.otp.entity.Otp;
import com.shoppingmall.otp.repository.OtpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final EmailSenderService emailSenderService;
    private final Logger LOGGER = LoggerFactory.getLogger(OtpServiceImpl.class);

    @Autowired
    public OtpServiceImpl(OtpRepository otpRepository, EmailSenderService emailSenderService) {
        this.otpRepository = otpRepository;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public String generateOtp(String email) throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstanceStrong();
        String code = String.valueOf(random.nextInt(9000) + 1000);
        LOGGER.debug("otp generated");
        return code;
    }

    @Override
    public void sendOtp(String email, String code) {
        String subject = "Otp 코드";
        String body = "서비스를 이용해주셔서 감사합니다.\n"
                + "코드: " + code + "\n"
                + "코드를 요청하지 않았다면 이 메일을 무시해주세요.";

        emailSenderService.sendEmail(email, subject, body);
        LOGGER.debug("otp sent");
    }

    @Override
    public void saveOtp(String email, String code) {
        Optional<Otp> o = otpRepository.findByEmail(email);
        if (o.isPresent()) {
            Otp otp = o.get();
            otp.setCode(code);
            otpRepository.save(otp);
            return;
        }
        Otp otp = Otp.builder().email(email).code(code).build();
        otpRepository.save(otp);
        LOGGER.debug("otp saved");
    }

    @Override
    public boolean checkOtp(String email, String code) {
        Optional<Otp> o = otpRepository.findByEmail(email);
        if (o.isPresent()) {
            Otp otp = o.get();
            if (otp.getCode().equals(code)) {
                LOGGER.debug("otp is correct");
                return true;
            }
        }
        LOGGER.debug("otp is wrong");
        return false;
    }
}
