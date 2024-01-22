package com.panneer.springlearn;

import com.panneer.springlearn.security.dto.RegisterRequest;
import com.panneer.springlearn.security.enums.Role;
import com.panneer.springlearn.security.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class SpringLearnApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLearnApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthenticationService service) {
        return args -> {
            var mongo = RegisterRequest.builder().firstname("user1").lastname("user1").email("user1@mail.com").password("password").role(Role.USER1).build();
            log.info("user1 token: [{}]", service.register(mongo).getAccessToken());

            var h2 = RegisterRequest.builder().firstname("user2").lastname("user2").email("user2@mail.com").password("password").role(Role.USER2).build();
            log.info("user2 token: [{}]", service.register(h2).getAccessToken());

            var all = RegisterRequest.builder().firstname("all").lastname("all").email("all@mail.com").password("password").role(Role.ALL).build();
            log.info("all token: [{}]", service.register(all).getAccessToken());
        };
    }

}
