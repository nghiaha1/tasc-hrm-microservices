package com.tasc.project.attendance.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.attendance.model.request.CheckInRequest;
import com.tasc.project.attendance.model.request.CheckOutRequest;
import com.tasc.project.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/attendance")
public class AttendanceController extends BaseController {
    @Autowired
    AttendanceService attendanceService;

    @PostMapping(path = "/check_in")
    public ResponseEntity<BaseResponseV2> checkInAttendance(@RequestBody CheckInRequest request) throws ApplicationException {
        return createdResponse(attendanceService.checkInAttendance(request));
    }

    @PutMapping(path = "/check_out/{attendanceId}")
    public ResponseEntity<BaseResponseV2> checkOutAttendance(@PathVariable long attendanceId,
                                                             @RequestBody CheckOutRequest request) throws ApplicationException {
        return createdResponse(attendanceService.checkOutAttendance(attendanceId, request));
    }

    @GetMapping
    public ResponseEntity<BaseResponseV2> findAll() throws ApplicationException {
        return createdResponse(attendanceService.findAll());
    }

    @GetMapping(path = "/employee/{id}")
    public ResponseEntity<BaseResponseV2> findAttendanceByEmployeeId(@PathVariable long id) throws ApplicationException {
        return createdResponse(attendanceService.getAttendanceByEmployeeId(id));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BaseResponseV2> findById(@PathVariable long id) throws ApplicationException {
        return createdResponse(attendanceService.findById(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable long id) throws ApplicationException {
        return createdResponse(attendanceService.delete(id));
    }

    @GetMapping(path = "/employee")
    public ResponseEntity<BaseResponseV2> findByEmployeeAndDateRange(@RequestParam long employeeId,
                                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) throws ApplicationException {
        return createdResponse(attendanceService.findByEmployeeAndDateRange(employeeId, startDate, endDate));
    }


}
