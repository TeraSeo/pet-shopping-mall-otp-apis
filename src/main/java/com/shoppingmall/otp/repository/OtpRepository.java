package com.shoppingmall.otp.repository;

import com.shoppingmall.otp.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findByEmail(String email);

}
