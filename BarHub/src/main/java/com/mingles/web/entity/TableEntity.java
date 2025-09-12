package com.mingles.web.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class TableEntity extends BaseEntity {
    private String tableName;
    private int capacity;
    private boolean active = true;
}
