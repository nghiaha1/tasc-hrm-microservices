package com.tasc.project.user.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.user.model.request.RoleRequest;
import com.tasc.project.user.service.RolePermissionService;
import com.tasc.project.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/role")
public class RoleController extends BaseController {
    @Autowired
    UserService userService;

    @Autowired
    RolePermissionService rolePermissionService;

    @PostMapping
    public ResponseEntity<BaseResponseV2> createRole(RoleRequest request) throws ApplicationException {
        return createdResponse(rolePermissionService.createRole(request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<BaseResponseV2> deleteRole(@PathVariable long id) throws ApplicationException {
        rolePermissionService.deleteRole(id);
        return createdResponse(HttpStatus.OK);
    }

    @PutMapping(path = "/{roleId}/permission/{permissionId}")
    public ResponseEntity<BaseResponseV2> assignPermissionToRole(@PathVariable long roleId,
                                                                 @PathVariable long permissionId) throws ApplicationException {
        return createdResponse(rolePermissionService.assignPermissionToRole(roleId, permissionId));
    }

    @DeleteMapping(path = "/{roleId}/permission/{permissionId}")
    public ResponseEntity<BaseResponseV2> removePermissionFromRole(@PathVariable long roleId,
                                                                   @PathVariable long permissionId) throws ApplicationException {
        rolePermissionService.removePermissionFromRole(roleId, permissionId);
        return createdResponse(HttpStatus.OK);
    }

}
