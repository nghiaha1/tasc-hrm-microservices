package com.tasc.project.attendance.model.request;

import com.tasc.entity.AttendanceStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceRequest {
    private long employeeId;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private AttendanceStatus attendanceStatus;

}
