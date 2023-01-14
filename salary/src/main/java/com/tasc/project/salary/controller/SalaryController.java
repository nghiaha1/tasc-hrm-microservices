package com.tasc.project.salary.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.salary.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/salary")
public class SalaryController extends BaseController {

    @Autowired
    SalaryService salaryService;

    @RequestMapping(path = "/calculate")
    public ResponseEntity<BaseResponseV2> calculateSalary(@RequestParam(name = "employee_id") long employeeId,
                                                          @RequestParam(name = "start_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                                          @RequestParam(name = "end_date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) throws ApplicationException {
        return createdResponse(salaryService.calculateSalary(employeeId, startDate, endDate));
    }
}
