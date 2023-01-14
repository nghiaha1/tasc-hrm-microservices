package com.tasc.project.employee.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.employee.model.request.CreateDepartmentRequest;
import com.tasc.project.employee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController extends BaseController {
    @Autowired
    DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<BaseResponseV2> create(@RequestBody CreateDepartmentRequest request) throws ApplicationException {
        return createdResponse(departmentService.create(request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponseV2> delete(@PathVariable long id) throws ApplicationException {
        return createdResponse(departmentService.delete(id));
    }

}
