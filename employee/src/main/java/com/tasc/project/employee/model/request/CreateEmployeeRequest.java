package com.tasc.project.employee.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeRequest {

    private String fullName;


    private String gender;

    private Date dob;

    private String email;

    private String phone;

    private String description;

    private String detail;

    private BigDecimal monthlyRate;

    private long departmentId;

}
