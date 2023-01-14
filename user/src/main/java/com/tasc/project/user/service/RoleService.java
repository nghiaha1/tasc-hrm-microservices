package com.tasc.project.user.service;

import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.model.ERROR;
import com.tasc.project.user.entity.Role;
import com.tasc.project.user.model.request.RoleRequest;
import com.tasc.project.user.repository.RoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public BaseResponseV2<Role> create(RoleRequest request) throws ApplicationException {
        validateRequest(request);

        Role role = Role.builder()
                .name(request.getName().toUpperCase())
                .description(request.getDescription())
                .build();

        roleRepository.save(role);

        return new BaseResponseV2<>(role);
    }

    public BaseResponseV2<Role> update(long id, RoleRequest request) throws ApplicationException {
        Optional<Role> optionalRole = roleRepository.findById(id);

        if (optionalRole.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found role with ID: " + id);
        }

        validateRequest(request);

        Role existedRole = optionalRole.get();

        existedRole.setName(request.getName().toUpperCase());
        existedRole.setDescription(request.getDescription());

        roleRepository.save(existedRole);

        return new BaseResponseV2<>(existedRole);
    }

    private void validateRequest(RoleRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Role's name is missing");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Role's description is missing");
        }
    }
}
