package com.mingles.web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name= "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 50)
    private RoleName roleName;

    private String description;

    @ManyToMany(mappedBy = "roleEntities")
    @ToString.Exclude
    private Set<UserEntity> userEntities;

    public enum RoleName {
        ADMIN,
        CUSTOMER,
        STAFF,
    }
}
