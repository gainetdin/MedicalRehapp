package com.telekom.javaschool.medicalrehapp.controller;

import com.telekom.javaschool.medicalrehapp.config.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String showIndex() {
        log.debug("Index page requested");
//        if (!"[ROLE_ANONYMOUS]".equals(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString())) {
//            CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            model.addAttribute("authName", currentUser.getFullName());
//            model.addAttribute("authRole", currentUser.getRole());
//        }
        return "index";
    }

    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }
}
