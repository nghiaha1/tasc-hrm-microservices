package com.tasc.model.dto;

import com.tasc.entity.AttendanceStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDTO {
    private long id;

    private long employeeId;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private AttendanceStatus attendanceStatus;

    private String reason;

}
