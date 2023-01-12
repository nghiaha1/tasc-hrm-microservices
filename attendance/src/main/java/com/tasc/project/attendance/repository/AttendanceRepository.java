package com.tasc.project.attendance.repository;

import com.tasc.project.attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {
    Attendance findAttendanceByEmployeeId(long employeeId);

    List<Attendance> findAttendanceByEmployeeIdAndDateBetween(long employeeId, LocalDate startDate, LocalDate endDate);

    List<Attendance> findAttendanceByDateBetween(LocalDate startDate, LocalDate endDate);
}
