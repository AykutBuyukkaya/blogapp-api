package com.buyukkaya.blogappapi;

import com.buyukkaya.blogappapi.user.service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlogappApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogappApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(RoleService roleService){
        return args ->
            roleService.addNewRole("USER");

    }
}
