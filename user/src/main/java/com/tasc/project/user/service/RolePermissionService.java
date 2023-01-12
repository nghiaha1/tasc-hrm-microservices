package com.tasc.project.user.service;

import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.model.ERROR;
import com.tasc.project.user.entity.Permission;
import com.tasc.project.user.entity.Role;
import com.tasc.project.user.entity.User;
import com.tasc.project.user.model.request.PermissionRequest;
import com.tasc.project.user.model.request.RoleRequest;
import com.tasc.project.user.repository.PermissionRepository;
import com.tasc.project.user.repository.RoleRepository;
import com.tasc.project.user.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolePermissionService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    UserRepository userRepository;

    public BaseResponseV2<Role> createRole(RoleRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Role name is empty");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Role description is empty");
        }

        Role role = Role.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        roleRepository.save(role);

        return new BaseResponseV2<Role>(role);
    }

    public void deleteRole(long id) throws ApplicationException {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found role with id: " + id);
        }

        roleRepository.deleteById(id);
    }

    public BaseResponseV2<Permission> createPermission(PermissionRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Permission name is empty");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Permission description is empty");
        }

        Permission permission = Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        permissionRepository.save(permission);

        return new BaseResponseV2<Permission>(permission);
    }

    public void deletePermission(long id) throws ApplicationException {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        if (optionalPermission.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found permission with id: " + id);
        }

        permissionRepository.deleteById(id);
    }

    public BaseResponseV2<User> assignRoleToUser(long userId, long roleId) throws ApplicationException {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if (optionalRole.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found role with id " + roleId);
        }

        if (optionalUser.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found user with id: " + userId);
        }

        User user = optionalUser.get();
        user.getRoles().add(optionalRole.get());

        userRepository.save(user);

        return new BaseResponseV2<User>(user);
    }

    public void removeRoleFromUser(long userId, long roleId) throws ApplicationException {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if (optionalRole.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found role with id " + roleId);
        }

        if (optionalUser.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found user with id: " + userId);
        }

        User user = optionalUser.get();
        user.getRoles().remove(optionalRole.get());

        userRepository.save(user);
    }

    public BaseResponseV2<Role> assignPermissionToRole(long roleId, long permissionId) throws ApplicationException {
        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if (optionalRole.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found role with id " + roleId);
        }

        if (optionalPermission.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found user with id: " + permissionId);
        }

        Role role = optionalRole.get();
        role.getPermissions().add(optionalPermission.get());
        roleRepository.save(role);

        return new BaseResponseV2<Role>(role);
    }

    public void removePermissionFromRole(long roleId, long permissionId) throws ApplicationException {
        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        Optional<Role> optionalRole = roleRepository.findById(roleId);

        if (optionalRole.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found role with id " + roleId);
        }

        if (optionalPermission.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found user with id: " + permissionId);
        }

        Role role = optionalRole.get();
        role.getPermissions().remove(optionalPermission.get());
        roleRepository.save(role);
    }
}
