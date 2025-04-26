package com.lopes.workhours;

import com.lopes.workhours.domain.entities.User;
import com.lopes.workhours.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String defaultUsername = "admin";
            User user = User.builder()
                    .name("Administrator")
                    .nickName("ADM")
                    .username(defaultUsername)
                    .password(passwordEncoder.encode("123"))
                    .build();
            userRepository.save(user);

            System.out.println("Default user created: " + defaultUsername);
        };
    }
}
