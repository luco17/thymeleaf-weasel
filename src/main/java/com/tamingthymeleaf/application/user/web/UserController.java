package com.tamingthymeleaf.application.user.web;

import com.tamingthymeleaf.application.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", service.getAllUsers());
        return "users/list";
    }
}
