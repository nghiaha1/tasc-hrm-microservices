package com.tasc.project.salary.connector;

import com.tasc.model.BaseResponseV2;
import com.tasc.model.dto.employee.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "employee-service", url = "http://localhost:8083/api/v1/employee")
public interface EmployeeConnector {
    @GetMapping(path = "/find/id/{id}")
    BaseResponseV2<EmployeeDTO> findById(@PathVariable long id);
}
