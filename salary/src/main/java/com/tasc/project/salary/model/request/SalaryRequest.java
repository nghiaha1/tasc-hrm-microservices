package com.tasc.project.salary.model.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryRequest {

    private long employeeId;

    private BigDecimal amount;

    private String payPeriod;

    private BigDecimal tax;

    private BigDecimal bonus;
}
