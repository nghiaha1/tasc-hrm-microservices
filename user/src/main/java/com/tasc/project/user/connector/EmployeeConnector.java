package com.tasc.project.user.connector;

import com.tasc.model.BaseResponseV2;
import com.tasc.model.dto.employee.EmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "employee-service", url = "${tasc_employee_address}")
public interface EmployeeConnector {
    @GetMapping(path = "/find/id/{id}")
    BaseResponseV2<EmployeeDTO> findById(@PathVariable long id);

    @PutMapping(path = "/{employeeId}/{userId}")
    BaseResponseV2<EmployeeDTO> updateUser(@PathVariable long employeeId,
                                           @PathVariable long userId);
}
