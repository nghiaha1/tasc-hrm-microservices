package com.tasc.project.salary.connector;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "employee-service", url = "http://localhost:80883/api/v1/employee")
public interface EmployeeConnector {

}
