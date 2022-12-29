package com.tasc.project.department.connector;

import com.tasc.model.BaseResponseV2;
import com.tasc.model.dto.employee.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "employee-service", url = "http://localhost:8083/api/v1/employee")
public interface EmployeeConnector {
    @GetMapping(path = "/{id}")
    BaseResponseV2<EmployeeDTO> findEmployeeById(@PathVariable long id);
}
