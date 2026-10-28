package org.example.information_security.controller;

import jakarta.validation.Valid;
import org.example.information_security.dto.request.AuthRequestDto;
import org.example.information_security.dto.responce.JwtAuthificationResponceDto;
import org.example.information_security.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtAuthificationResponceDto> register(@Valid @RequestBody AuthRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthificationResponceDto> login(@Valid @RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.singIn(request));
    }
}