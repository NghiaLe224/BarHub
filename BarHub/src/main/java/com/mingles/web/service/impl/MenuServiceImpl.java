package com.mingles.web.service.impl;

import com.mingles.web.dto.common.ApiResponse;
import com.mingles.web.dto.common.PagingResponse;
import com.mingles.web.dto.menu.CreateMenuItemRequest;
import com.mingles.web.dto.menu.MenuItemResponse;
import com.mingles.web.entity.MenuItemEntity;
import com.mingles.web.repository.MenuRepository;
import com.mingles.web.service.MenuService;
import com.mingles.web.service.S3Service;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final S3Service s3Service;

    @Override
    public ApiResponse<PagingResponse<MenuItemResponse>> browseMenu(Pageable pageable, String keyword) {
        Specification<MenuItemEntity> spec = (root, query, cb) -> cb.conjunction();

        if (keyword != null && !keyword.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"));
        }

        Page<MenuItemEntity> menuItems = menuRepository.findAll(spec, pageable);
        Page<MenuItemResponse> menuItemResponses = menuItems
                .map(i -> modelMapper.map(i, MenuItemResponse.class));
        PagingResponse<MenuItemResponse> pagingResponse = new PagingResponse<>(menuItemResponses);
        return new ApiResponse<>(pagingResponse, "Fetched all menu items successfully", LocalDateTime.now());
    }

    @Override
    public ApiResponse<MenuItemResponse> createMenuItem(CreateMenuItemRequest request) {
        if(menuRepository.existsByName(request.getName())){
            throw new EntityExistsException("Menu already exists");
        }
        MenuItemEntity menuItemEntity = modelMapper.map(request, MenuItemEntity.class);
        if(request.getImage() != null && !request.getImage().isEmpty()) {
            String url = s3Service.uploadFile(request.getImage());
            menuItemEntity.setImageUrl(url);
        }
        menuRepository.save(menuItemEntity);

        MenuItemResponse menuItemResponse = modelMapper.map(menuItemEntity, MenuItemResponse.class);

        return new ApiResponse<>(menuItemResponse, "Created menu item successfully", LocalDateTime.now());
    }

    @Override
    public ApiResponse<MenuItemResponse> updateMenuItem(Long id, CreateMenuItemRequest request) {
        MenuItemEntity menuItem = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu item with id " + id + " not found"));

        modelMapper.map(request, menuItem);

        if(request.getImage() != null && !request.getImage().isEmpty()) {
            String url = s3Service.uploadFile(request.getImage());
            menuItem.setImageUrl(url);
        }

        MenuItemResponse response = modelMapper.map(menuRepository.save(menuItem), MenuItemResponse.class);

        return new ApiResponse<>(response, "Updated menu item successfully", LocalDateTime.now());
    }

    @Override
    public void deleteMenuItem(Long id) {
        MenuItemEntity menuItem = menuRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Menu item with id " + id + " not found"));
        menuRepository.delete(menuItem);
    }
}
