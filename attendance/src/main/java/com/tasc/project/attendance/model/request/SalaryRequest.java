package com.tasc.project.attendance.model.request;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryRequest {

    private long employeeId;

    private LocalDate startDate;

    private LocalDate endDate;
}
