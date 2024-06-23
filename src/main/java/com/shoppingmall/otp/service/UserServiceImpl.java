package com.shoppingmall.otp.service;

import com.shoppingmall.otp.entity.Role;
import com.shoppingmall.otp.entity.User;
import com.shoppingmall.otp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getRole(String email) {
        Optional<User> u = userRepository.findByEmail(email);
        if (u.isPresent()) {
            User user = u.get();
            Role role = user.getRole();
            return role.toString();
        }
        throw new UsernameNotFoundException("User not exists");
    }

    @Override
    public void setVerifiedStatus(String email, boolean isVerified) {
        Optional<User> u = userRepository.findByEmail(email);
        if (u.isPresent()) {
            User user = u.get();
            user.setIsVerified(isVerified);
            userRepository.save(user);
            return;
        }
        throw new UsernameNotFoundException("User not exists");
    }
}
