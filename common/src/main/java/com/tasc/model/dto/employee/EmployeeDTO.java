package com.tasc.model.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tasc.entity.BaseStatus;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO implements Serializable {
    private long id;

    private String fullName;

    private String gender;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dob;

    private String email;

    private String phone;

    private BigDecimal salary;


    private String description;

    private String detail;

    private long userId;

    private BaseStatus status;

    private BigDecimal montlyRate;

    private long departmentId;

}
