package com.tasc.project.user.connector;

import com.tasc.model.BaseResponseV2;
import com.tasc.model.dto.employee.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "employee-service", url = "${tasc_employee_address}")
public interface EmployeeConnector {
    @GetMapping(path = "/{id}")
    BaseResponseV2<EmployeeDTO> findById(@PathVariable long id);
}
