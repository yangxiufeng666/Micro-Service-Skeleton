package com.microservice.skeleton.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthCenterApplicationTests {
	@Test
	public void contextLoads() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("123456"));
		System.out.println(passwordEncoder.matches("123456","$2a$10$Auj1gIQ7y2CqZkz1P0/GNOV4CNVjZ8qjeUVgztU7GjL6sF/08ebVO"));
	}

}
