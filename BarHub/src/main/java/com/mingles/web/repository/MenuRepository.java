package com.mingles.web.repository;

import com.mingles.web.entity.MenuItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuItemEntity,Long>, JpaSpecificationExecutor<MenuItemEntity> {

    boolean existsByName(String name);
}
