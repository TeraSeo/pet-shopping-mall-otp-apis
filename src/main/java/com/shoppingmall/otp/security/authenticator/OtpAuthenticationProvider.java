package com.shoppingmall.otp.security.authenticator;

import com.shoppingmall.otp.security.authentication.OtpAuthentication;
import com.shoppingmall.otp.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    private final OtpService otpService;

    @Autowired
    public OtpAuthenticationProvider(OtpService otpService) {
        this.otpService = otpService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String code = authentication.getCredentials().toString();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        boolean isCorrect = otpService.checkOtp(email, code);
        if (isCorrect) {
            return new OtpAuthentication(email, code, authorities);
        }

        throw new BadCredentialsException("Bad credentials");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.isAssignableFrom(authentication);
    }
}
