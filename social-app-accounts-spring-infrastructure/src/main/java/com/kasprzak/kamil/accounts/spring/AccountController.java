package com.kasprzak.kamil.accounts.spring;


import com.kasprzak.kamil.accounts.domain.entity.User;
import com.kasprzak.kamil.accounts.domain.request.RegisterRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kasprzak.kamil.accounts.logic.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {

    private final UserService userService;

    @PostMapping("/auth/register")
    public void register(@RequestBody RegisterRequest request) {
        User newUser = User
                .builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .roles(List.of( userService.getRole("USER")))
                .build();
        userService.saveUser(newUser);
    }

    @PostMapping("/auth/login")
    public void login() {
        // code is in com.kasprzak.kamil.accounts.application.filter
    }
}
