package com.tasc.project.salary.entity;

import com.tasc.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "salaries")
public class Salary extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long employeeId;

    private BigDecimal amount;

    private String payPeriod;

    private BigDecimal tax;

    private BigDecimal bonus;
}
