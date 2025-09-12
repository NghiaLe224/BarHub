package com.mingles.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tables")
public class TableEntity extends BaseEntity {
    private String tableName;
    private int capacity;
    private boolean active = true;
}
