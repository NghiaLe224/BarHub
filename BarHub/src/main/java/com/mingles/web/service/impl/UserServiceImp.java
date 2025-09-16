package com.mingles.web.service.impl;

import com.mingles.web.dto.user.UserResponse;
import com.mingles.web.entity.RoleEntity;
import com.mingles.web.entity.UserEntity;
import com.mingles.web.repository.UserRepository;
import com.mingles.web.service.UserService;
import com.mingles.web.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserResponse getUser() {
        var userDetails = AuthUtil.getCurrentUserDetails();
        if (userDetails != null){
            UserEntity user = getUserByUsername(userDetails.getUsername());
            if (user!=null){
                List<String> roleNames = user.getRoleEntities().stream()
                        .map(RoleEntity::getRoleName)
                        .map(Enum::name)
                        .collect(Collectors.toList());
                return UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .roles(roleNames)
                        .build();
            }
        }
        return null;
    }
}
