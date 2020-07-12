package com.example.controller;

import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Set;

@Controller
public class LoginController {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    static Logger log = Logger.getLogger(LoginController.class);


    @GetMapping
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.setViewName("login");

        if (auth != null) {
            User user = userService.findUserByEmail(auth.getName());
            if (user != null) {
                log.debug("Пользователь авторизовался! " + user.getEmail());
                modelAndView.setViewName("redirect:/");
            }
        }
        return modelAndView;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (user != null) {

            modelAndView.setViewName("redirect:/");
        } else {

            User user1 = new User();
            modelAndView.addObject("user", user1);
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Уже зарегистрирован пользователь с указанным e-mail адресом");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Вы успешно зарегистрировались!");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @GetMapping("/admin/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.getName());
        User user = userService.findUserByEmail(auth.getName());

        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.getRole().equals("ADMIN"))
                modelAndView.addObject("adminMessage", "Добро пожаловать " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
            else
                modelAndView.addObject("adminMessage", "Содержимое доступно только для пользователей с ролью администратора");
        }
        modelAndView.setViewName("admin/home");
        return modelAndView;
    }

}
