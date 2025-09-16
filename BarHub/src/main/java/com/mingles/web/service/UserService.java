package com.mingles.web.service;

import com.mingles.web.entity.UserEntity;

public interface UserService {
    UserEntity getUserByUsername(String username);
}
