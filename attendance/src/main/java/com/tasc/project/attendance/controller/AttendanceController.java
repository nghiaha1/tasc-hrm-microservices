package com.tasc.project.attendance.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.attendance.model.request.AttendanceRequest;
import com.tasc.project.attendance.search.SearchBody;
import com.tasc.project.attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/attendance")
public class AttendanceController extends BaseController {
    @Autowired
    AttendanceService attendanceService;

    @PostMapping(path = "/create")
    public ResponseEntity<BaseResponseV2> saveAttendance(AttendanceRequest request) throws ApplicationException {
        return createdResponse(attendanceService.saveAttendance(request));
    }

    @GetMapping(path = "/employee")
    public ResponseEntity<BaseResponseV2> findAttendanceByEmployeeId(@RequestParam(name = "page", defaultValue = "1") int page,
                                                                     @RequestParam(name = "page_size", defaultValue = "10") int pageSize,
                                                                     @RequestParam(name = "employee_id", defaultValue = "-1") long employeeId,
                                                                     @RequestParam(name = "start_date", required = false) String startDate,
                                                                     @RequestParam(name = "end_date", required = false)  String endDate,
                                                                     @RequestParam(name = "status", defaultValue = "-1") int status) throws ApplicationException {

        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withPageSize(pageSize)
                .withEmployeeId(employeeId)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .withStatus(status)
                .build();

        return createdResponse(attendanceService.findAll(searchBody));
    }
}
