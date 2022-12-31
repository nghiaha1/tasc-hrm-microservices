package com.tasc.project.department.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tasc.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "departments")
public class Department extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private String detail;

    @Lob
    private List<String> employeeList;

    @ManyToOne
    @JoinTable(name = "department_relationship",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id"))
    @JsonBackReference
    private Department parentDepartment;

    @OneToMany(mappedBy = "parentDepartment")
    @JsonBackReference
    private List<Department> childrenDepartment;

    private int isRoot;
}
