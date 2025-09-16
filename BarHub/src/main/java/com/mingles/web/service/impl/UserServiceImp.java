package com.mingles.web.service.impl;

import com.mingles.web.entity.UserEntity;
import com.mingles.web.repository.UserRepository;
import com.mingles.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
