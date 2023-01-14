package com.tasc.model.dto.department;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDTO implements Serializable {

    private long id;
    private String name;

    private String description;

    private String detail;

    private List<Long> employeeList;

    private String parentDepartment;

    private List<Long> childrenDepartmentList;

    private int isRoot;
}
