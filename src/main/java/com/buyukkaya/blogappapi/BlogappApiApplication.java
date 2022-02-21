package com.buyukkaya.blogappapi;

import com.buyukkaya.blogappapi.user.model.entity.Role;
import com.buyukkaya.blogappapi.user.model.entity.UserEntity;
import com.buyukkaya.blogappapi.user.repository.UserRepository;
import com.buyukkaya.blogappapi.user.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@SpringBootApplication
public class BlogappApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogappApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(RoleService roleService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            roleService.addNewRole("ROLE_USER");
            roleService.addNewRole("ROLE_ADMIN");

            UserEntity userEntity = new UserEntity("admin"
                    , "admin@buyukkaya.com", passwordEncoder.encode("admin"));
            Collection<Role> roles = new ArrayList<>();
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
            userEntity.setRole(roles);

            userRepository.save(userEntity);

        };
    }
}
