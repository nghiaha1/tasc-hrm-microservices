package com.tasc.project.attendance.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.attendance.model.request.AttendanceRequest;
import com.tasc.project.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/attendance")
public class AttendanceController extends BaseController {
    @Autowired
    AttendanceService attendanceService;

    @PostMapping(path = "/create")
    public ResponseEntity<BaseResponseV2> saveAttendance(AttendanceRequest request) throws ApplicationException {
        return createdResponse(attendanceService.saveAttendance(request));
    }

//    @GetMapping(path = "/employee")
//    public ResponseEntity<BaseResponseV2> findAllAttendance(@RequestParam(name = "page", defaultValue = "1") int page,
//                                                            @RequestParam(name = "page_size", defaultValue = "10") int pageSize,
//                                                            @RequestParam(name = "employee_id", defaultValue = "-1") long employeeId,
//                                                            @RequestParam(name = "start_date", required = false) String startDate,
//                                                            @RequestParam(name = "end_date", required = false) String endDate,
//                                                            @RequestParam(name = "status", defaultValue = "-1") int status) throws ApplicationException {
//
//        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
//                .withPage(page)
//                .withPageSize(pageSize)
//                .withEmployeeId(employeeId)
//                .withStartDate(startDate)
//                .withEndDate(endDate)
//                .withStatus(status)
//                .build();
//
//        return createdResponse(attendanceService.findAll(searchBody));
//    }

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

    @GetMapping(path = "/employee/{employeeId}/{startDate}/{endDate}")
    public ResponseEntity<BaseResponseV2> findByEmployeeAndDateRange(@PathVariable long employeeId,
                                                                     @PathVariable LocalDate startDate,
                                                                     @PathVariable LocalDate endDate) throws ApplicationException {
        return createdResponse(attendanceService.findByEmployeeAndDateRange(employeeId, startDate, endDate));
    }
}
