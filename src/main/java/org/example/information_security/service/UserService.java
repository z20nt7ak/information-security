package org.example.information_security.service;

import org.example.information_security.dto.responce.UserResponceDto;
import org.example.information_security.model.User;
import org.example.information_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponceDto> getAll() {
        List<UserResponceDto> result = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            result.add(new UserResponceDto(user.getId(), HtmlUtils.htmlEscape(user.getUsername())));
        }
        return result;
    }
}