package com.mingles.web.controller;

import com.mingles.web.dto.common.ApiResponse;
import com.mingles.web.dto.common.PagingResponse;
import com.mingles.web.dto.menu.CreateMenuItemRequest;
import com.mingles.web.dto.menu.MenuItemResponse;
import com.mingles.web.service.MenuService;
import com.mingles.web.util.PaginationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static com.mingles.web.constant.AppConstant.*;

//Browse Menu (Guest/User)
//CRUD Menu (Admin) → upload ảnh S3
//View Top Drinks (Analytics, optional query)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/menu")
    public ResponseEntity<ApiResponse<PagingResponse<MenuItemResponse>>> browseMenuItem(
            @RequestParam(name = "pageNumber", required = false, defaultValue = DEFAULT_PAGE + "") int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = DEFAULT_SIZE + "") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = DEFAULT_SORT_BY) String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = DEFAULT_SORT_DIR) String sortDir,
            @RequestParam(name = "keyword", required = false) String keyword
    ){
        Pageable pageable = PaginationUtils.createPageable(pageNumber, size, sortBy, sortDir);
        return new ResponseEntity<>(menuService.browseMenu(pageable, keyword), HttpStatus.OK);
    }

    @PostMapping("/admin/menu")
    public ResponseEntity<ApiResponse<MenuItemResponse>> createMenuItem(
            @Valid @RequestPart CreateMenuItemRequest request
    ) {
        ApiResponse<MenuItemResponse> response = menuService.createMenuItem(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/admin/menu/{id}")
    public ResponseEntity<ApiResponse<MenuItemResponse>> updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestPart CreateMenuItemRequest request
    ){
        ApiResponse<MenuItemResponse> response = menuService.updateMenuItem(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/admin/menu/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMenuItem(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(new ApiResponse<>(
                null, "Deleted menu item successfully", LocalDateTime.now()));
    }
}
