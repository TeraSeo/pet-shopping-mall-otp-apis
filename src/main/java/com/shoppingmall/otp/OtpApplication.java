package com.shoppingmall.otp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OtpApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtpApplication.class, args);
	}

}
