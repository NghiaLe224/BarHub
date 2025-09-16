package com.mingles.web.service;

import com.mingles.web.dto.user.UserResponse;
import com.mingles.web.entity.UserEntity;

public interface UserService {
    UserEntity getUserByUsername(String username);

    UserResponse getUser();
}
