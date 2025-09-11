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
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 50)
    private RoleName roleName;

    private String description;

    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private Set<User> users;

    public enum RoleName {
        ROLE_ADMIN,
        ROLE_CUSTOMER,
        ROLE_STAFF,
    }
}
