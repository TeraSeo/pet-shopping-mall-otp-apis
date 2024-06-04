package com.shoppingmall.otp.security.filter;

import com.shoppingmall.otp.security.authenticator.OtpAuthenticationProvider;
import com.shoppingmall.otp.security.jwt.JwtToken;
import com.shoppingmall.otp.security.jwt.JwtTokenProvider;
import com.shoppingmall.otp.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class OtpAuthenticationFilter extends OncePerRequestFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(OtpAuthenticationFilter.class);
    private final OtpAuthenticationProvider otpAuthenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @Autowired
    public OtpAuthenticationFilter(OtpAuthenticationProvider otpAuthenticationProvider, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.otpAuthenticationProvider = otpAuthenticationProvider;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String email = request.getHeader("email");
        String code = request.getHeader("code");

        LOGGER.debug("otp filter start");

        if (email != null && code != null) {
            String role = userService.getRole(email);
            GrantedAuthority auth = new SimpleGrantedAuthority(role);
            Authentication a = new UsernamePasswordAuthenticationToken(email, code, List.of(auth));
            otpAuthenticationProvider.authenticate(a);
            JwtToken token = jwtTokenProvider.generateToken(a);

            response.setHeader("Authorization", token.getAccessToken());
            response.setHeader("Refresh", token.getRefreshToken());

            SecurityContextHolder.getContext().setAuthentication(a);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/otp/send");
    }
}
