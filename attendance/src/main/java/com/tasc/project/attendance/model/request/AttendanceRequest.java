package com.tasc.project.attendance.model.request;

import com.tasc.entity.AttendanceStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceRequest {
    private long employeeId;

    private AttendanceStatus attendanceStatus;

    private String reason;

}
