package com.kasprzak.kamil.accounts.spring;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kasprzak.kamil.accounts.domain.entity.Role;
import com.kasprzak.kamil.accounts.domain.entity.User;
import com.kasprzak.kamil.accounts.domain.request.RegisterRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kasprzak.kamil.accounts.logic.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
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

    @PostMapping("/auth/refresh")
    public ResponseEntity<Role> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUserByName(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", accessToken);
                tokens.put("refresh_token", refreshToken);
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                log.info("Refreh token");
            } catch (Exception e) {
                log.error("Error login in {}", e.getMessage());
                response.setStatus(403);
                Map<String, String> error = new HashMap<>();
                error.put("error_maessage", e.getMessage());
                response.setContentType("application/json");
            }

        } else {
            throw new RuntimeException("REfresh token is missing");
        }
        return ResponseEntity.ok().build();
    }
}
