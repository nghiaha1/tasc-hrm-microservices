package com.tasc.project.employee.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tasc.entity.BaseEntity;
import com.tasc.project.employee.util.MoneySerializer;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fullName;

    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String email;

    private String phone;

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal salary;

    @Lob
    private String description;

    @Lob
    private String detail;

    @JsonSerialize(using = MoneySerializer.class)
    private BigDecimal monthlyRate;

    // relationship
    @ManyToOne
    @JoinTable(name = "department_employee",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private Department department;

    private long userId;
}
