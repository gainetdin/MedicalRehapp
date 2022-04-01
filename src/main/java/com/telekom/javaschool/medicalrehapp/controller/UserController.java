package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.dto.UserDto;
import com.telekom.javaschool.medicalrehapp.entity.Role;
import com.telekom.javaschool.medicalrehapp.service.UserManager;
import com.telekom.javaschool.medicalrehapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserManager userManager;

    @Autowired
    public UserController(UserService userService, UserManager userManager) {
        this.userService = userService;
        this.userManager = userManager;
    }

    @GetMapping("/add")
    public String showUserAddForm() {
        return "user-add";
    }

    @PostMapping("/add")
    public String addUser(UserDto user) {
        userService.create(user);
        return "redirect:/login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user-list";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{login}")
    public String showUserEditForm(@PathVariable("login") String login, Model model) {
        model.addAttribute("user", userService.findByLogin(login));
        model.addAttribute("roles", Role.values());
        return "user-edit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/edit")
    public String editUser(UserDto user) {
        log.debug(user.toString());
        userManager.updateUserAndCheckRole(user);
        return "redirect:/user";
    }
}
