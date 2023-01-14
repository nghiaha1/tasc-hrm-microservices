package com.tasc.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkIn;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkOut;

    private AttendanceStatus attendanceStatus;

    private String reason;

}
