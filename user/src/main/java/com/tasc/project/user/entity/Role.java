package com.tasc.project.user.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tasc.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;
    @OneToMany(mappedBy = "role")
    @JsonBackReference
    private Set<User> users;
}
