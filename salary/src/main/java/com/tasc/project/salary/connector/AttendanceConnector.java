package com.tasc.project.salary.connector;

import com.tasc.model.BaseResponseV2;
import com.tasc.model.dto.AttendanceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "attendance-service", url = "http://localhost:8086/api/v1/attendance")
public interface AttendanceConnector {

    @GetMapping(path = "/employee")
    BaseResponseV2<List<AttendanceDTO>> findByEmployeeAndDateRange(@RequestParam long employeeId,
                                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate);
}
