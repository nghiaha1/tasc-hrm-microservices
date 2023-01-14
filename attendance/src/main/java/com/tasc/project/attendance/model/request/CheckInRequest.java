package com.tasc.project.attendance.model.request;

import com.tasc.entity.AttendanceStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckInRequest {
    private long employeeId;


}
