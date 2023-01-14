package com.tasc.project.attendance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tasc.entity.AttendanceStatus;
import com.tasc.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "attendances")
public class Attendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long employeeId;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime checkIn;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime checkOut;

    private AttendanceStatus attendanceStatus;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate date;
}
