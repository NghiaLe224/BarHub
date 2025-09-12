package com.mingles.web.service;

import com.mingles.web.dto.common.ApiResponse;
import com.mingles.web.dto.common.PagingResponse;
import com.mingles.web.dto.menu.CreateMenuItemRequest;
import com.mingles.web.dto.menu.MenuItemResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

public interface MenuService {
    ApiResponse<PagingResponse<MenuItemResponse>> browseMenu(Pageable pageable, String keyword);

    ApiResponse<MenuItemResponse> createMenuItem(@Valid CreateMenuItemRequest request);

    ApiResponse<MenuItemResponse> updateMenuItem(Long id, @Valid CreateMenuItemRequest request);

    void deleteMenuItem(Long id);
}
