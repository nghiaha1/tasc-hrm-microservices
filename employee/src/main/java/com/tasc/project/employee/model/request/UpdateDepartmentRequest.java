package com.tasc.project.employee.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDepartmentRequest {

    private String name;

    private String description;

    private String detail;

}
