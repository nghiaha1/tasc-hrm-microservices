package com.tasc.project.salary.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.salary.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/salary")
public class SalaryController extends BaseController {

    @Autowired
    SalaryService salaryService;

    @RequestMapping(path = "/calculate/{employeeId}/{startDate}/{endDate}")
    public ResponseEntity<BaseResponseV2> calculateSalary(@PathVariable long employeeId,
                                                          @PathVariable LocalDate startDate,
                                                          @PathVariable LocalDate endDate) throws ApplicationException {
        return createdResponse(salaryService.calculateSalary(employeeId, startDate, endDate));
    }
}
