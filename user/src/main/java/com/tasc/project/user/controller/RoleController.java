package com.tasc.project.user.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.user.model.request.RoleRequest;
import com.tasc.project.user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/role")
public class RoleController extends BaseController {
    @Autowired
    RoleService roleService;

    @PostMapping
    public ResponseEntity<BaseResponseV2> create(RoleRequest request) throws ApplicationException {
        return createdResponse(roleService.create(request));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BaseResponseV2> update(@PathVariable long id,
                                                 @RequestBody RoleRequest request) throws ApplicationException {
        return createdResponse(roleService.update(id, request));
    }


}
