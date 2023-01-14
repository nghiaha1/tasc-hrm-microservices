package com.tasc.project.employee.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tasc.entity.BaseEntity;
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

    @Column(length = 1)
    private String gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    private String email;

    private String phone;

    private BigDecimal salary;

    private BigDecimal bonus;

    private String description;

    @Lob
    private String detail;

    private BigDecimal hourlyRate;

    // relationship
    @ManyToOne
    @JoinTable(name = "department_employee",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private Department department;

    @Column(nullable = true)
    private long userId;

    private long positionId;

}
