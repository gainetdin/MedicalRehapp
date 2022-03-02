package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.Role;
import com.telekom.javaschool.medicalrehapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add-user")
    public String addNewUser() {
        return "add-user";
    }

    @PostMapping("/add-user")
    public String addUser(UserDto user) {
        userService.create(user);
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @GetMapping("/user/{login}")
    public String userEdit(@PathVariable("login") String login, Model model) {
        model.addAttribute("user", userService.findByLogin(login));
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PostMapping("/user-edit")
    public String userSave(UserDto user) {
        log.debug(user.toString());
        userService.update(user);
        return "redirect:/user";
    }
}
