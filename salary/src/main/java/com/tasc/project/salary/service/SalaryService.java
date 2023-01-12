package com.tasc.project.salary.service;

import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.salary.connector.EmployeeConnector;
import com.tasc.project.salary.entity.Salary;
import com.tasc.project.salary.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SalaryService {
    @Autowired
    SalaryRepository salaryRepository;

    @Autowired
    EmployeeConnector employeeConnector;

    public BaseResponseV2<Salary> calculateSalary(long employeeId, LocalDate payPeriodStart, LocalDate payPeriodEnd) throws ApplicationException {
        BaseResponseV2
    }
}
