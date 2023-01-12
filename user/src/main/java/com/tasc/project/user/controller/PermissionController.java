package com.tasc.project.user.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.user.model.request.PermissionRequest;
import com.tasc.project.user.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/permission")
public class PermissionController extends BaseController {
    @Autowired
    RolePermissionService rolePermissionService;

    @PostMapping
    public ResponseEntity<BaseResponseV2> createPermission(PermissionRequest request) throws ApplicationException {
        return createdResponse(rolePermissionService.createPermission(request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deletePermission(@PathVariable long id) throws ApplicationException {
        rolePermissionService.deletePermission(id);
        return createdResponse(HttpStatus.OK);
    }
}
