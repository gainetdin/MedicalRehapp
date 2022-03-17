package com.telekom.javaschool.medicalrehapp.config;

import com.telekom.javaschool.medicalrehapp.entity.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        authorities.forEach(authority -> sendRedirectByAuthority(request, response, authority));
    }

    private void sendRedirectByAuthority(HttpServletRequest request,
                                         HttpServletResponse response,
                                         GrantedAuthority authority) {
        String successUrl = selectUrlByAuthority(authority);
        try {
            redirectStrategy.sendRedirect(request, response, successUrl);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String selectUrlByAuthority(GrantedAuthority authority) {
        String successUrl;
        String formattedAuthority = authority.getAuthority().replace("ROLE_", "");
        switch (Role.valueOf(formattedAuthority)) {
            case DOCTOR:
                successUrl = "/patient";
                break;
            case NURSE:
                successUrl = "/event";
                break;
            case ADMIN:
                successUrl = "/user";
                break;
            case USER:
                successUrl = "/treatment";
                break;
            default:
                successUrl = "/index";
                break;
        }
        return successUrl;
    }
}
