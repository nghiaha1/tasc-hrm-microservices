package com.tasc.project.employee.controller;

import com.tasc.controller.BaseController;
import com.tasc.entity.BaseStatus;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.employee.model.request.CreateDepartmentRequest;
import com.tasc.project.employee.model.request.UpdateDepartmentRequest;
import com.tasc.project.employee.service.DepartmentService;
import net.datafaker.providers.base.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/department")
public class DepartmentController extends BaseController {
    @Autowired
    DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<BaseResponseV2> findAll(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(name = "page_size", defaultValue = "10") int pageSize,
                                                  @RequestParam(defaultValue = "ACTIVE") String status) throws ApplicationException {
        return createdResponse(departmentService.findAll(BaseStatus.valueOf(status), page, pageSize));
    }

    @GetMapping(path = "/parent/{name}")
    public ResponseEntity<BaseResponseV2> findParentsByChild_1(@PathVariable String name) throws ApplicationException {
        return createdResponse(departmentService.findParentsByChild(name));
    }

    @GetMapping(path = "/children/{name}")
    public ResponseEntity<BaseResponseV2> findChildrenByParent_1(@PathVariable String name) throws ApplicationException {
        return createdResponse(departmentService.findChildrenByParent(name));
    }

    @PostMapping
    public ResponseEntity<BaseResponseV2> create(@RequestBody CreateDepartmentRequest request) throws ApplicationException {
        return createdResponse(departmentService.create(request));
    }

    @PutMapping(path = "{departmentId}")
    private ResponseEntity<BaseResponseV2> update(@PathVariable long departmentId,
                                                  @RequestBody UpdateDepartmentRequest request) throws ApplicationException {
        return createdResponse(departmentService.update(departmentId, request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponseV2> delete(@PathVariable long id) throws ApplicationException {
        return createdResponse(departmentService.delete(id));
    }

}
