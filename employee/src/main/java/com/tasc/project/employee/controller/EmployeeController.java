package com.tasc.project.employee.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.employee.model.request.CreateEmployeeRequest;
import com.tasc.project.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employee")
public class EmployeeController extends BaseController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<BaseResponseV2> createEmployee(@RequestBody CreateEmployeeRequest request) throws ApplicationException {
        return createdResponse(employeeService.createdEmployee(request));
    }

    @GetMapping
    public ResponseEntity<BaseResponseV2> findAll(@RequestParam(required = false) String status,
                                                  @RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(name = "page_size", defaultValue = "10") int pageSize)
            throws ApplicationException {
        return createdResponse(employeeService.findAll(status, page, pageSize));
    }

    @GetMapping(path = "/find/id/{id}")
    public ResponseEntity<BaseResponseV2> findById(@PathVariable long id) throws ApplicationException {
        return createdResponse(employeeService.findById(id));
    }

    @GetMapping(path = "/find/name/{fullName}")
    public ResponseEntity<BaseResponseV2> findByFullName(@PathVariable String fullName) throws ApplicationException {
        return createdResponse(employeeService.findByFullName(fullName));
    }

    @PutMapping(path = "/{employeeId}/{userId}")
    public ResponseEntity<BaseResponseV2> updateUser(@PathVariable long employeeId,
                                                     @PathVariable long userId) throws ApplicationException {
        return createdResponse(employeeService.updateUser(employeeId, userId));
    }
}
