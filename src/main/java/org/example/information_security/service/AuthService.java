package org.example.information_security.service;

import org.example.information_security.dto.request.AuthRequestDto;
import org.example.information_security.dto.responce.JwtAuthificationResponceDto;
import org.example.information_security.exceptions.BadCredentialsException;
import org.example.information_security.exceptions.UserAlreadyExistsException;
import org.example.information_security.model.User;
import org.example.information_security.repository.UserRepository;
import org.example.information_security.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public AuthService(UserRepository userRepository, UserDetailsService userDetailsService,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public JwtAuthificationResponceDto register(AuthRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return jwtService.generateAuthToken(userDetailsService.loadUserByUsername(user.getUsername()));
    }

    public JwtAuthificationResponceDto singIn(AuthRequestDto request) {
        User user = findByCredentials(request);
        return jwtService.generateAuthToken(
                userDetailsService.loadUserByUsername(user.getUsername())
        );
    }

    private User findByCredentials(AuthRequestDto request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return user;
            }
        }
        throw new BadCredentialsException("Неверный логин или пароль");
    }
}