package com.example;

import com.example.controller.LoginController;
import com.example.model.Role;
import com.example.repository.RoleRepository;
import com.example.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springsecurity2Application {
    static Logger log = Logger.getLogger(Springsecurity2Application.class);

    public static void main(String[] args) {
//        log.debug("Пользователь зарегистровался!");
        SpringApplication.run(Springsecurity2Application.class, args);
    }
}
