package com.tasc.project.employee.service;

import com.tasc.entity.BaseStatus;
import com.tasc.entity.Constant;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.model.ERROR;
import com.tasc.model.dto.department.DepartmentDTO;
import com.tasc.project.employee.entity.Department;
import com.tasc.project.employee.entity.DepartmentEmployee;
import com.tasc.project.employee.entity.Employee;
import com.tasc.project.employee.model.request.CreateDepartmentRequest;
import com.tasc.project.employee.model.request.UpdateDepartmentRequest;
import com.tasc.project.employee.repository.DepartmentEmployeeRepository;
import com.tasc.project.employee.repository.DepartmentRepository;
import com.tasc.project.employee.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import net.datafaker.providers.base.App;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentEmployeeRepository departmentEmployeeRepository;

    public BaseResponseV2 findAll(BaseStatus status, int page, int pageSize) throws ApplicationException {
        return new BaseResponseV2(departmentRepository.findAllByStatus(status, PageRequest.of(page, pageSize)));
    }

    public BaseResponseV2 findParentsByChild(String name) throws ApplicationException {

        List<Department> optionalDepartment = departmentRepository.findDepartmentByNameContaining(name);

        if (optionalDepartment.isEmpty()) {
            log.info("Not found parents department with child id {}", name);
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found parents department with child name " + name);
        }

        return new BaseResponseV2<>(departmentRepository.findParentsByChild(name));
    }

    public BaseResponseV2 findChildrenByParent(String name) throws ApplicationException {

        List<Department> optionalDepartment = departmentRepository.findDepartmentByNameContaining(name);

        if (optionalDepartment.isEmpty()) {
            log.info("Not found children department with parent id {}", name);
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found children department with parent name " + name);
        }

        return new BaseResponseV2<>(departmentRepository.findChildrenByParent(name));
    }

    public BaseResponseV2<DepartmentDTO> create(CreateDepartmentRequest request) throws ApplicationException {
        validateCreateRequest(request);

        if (!request.checkIsRoot()) {
            Optional<Department> checkParentOpt = departmentRepository.findById(request.getParentDepartmentId());

            if (checkParentOpt.isEmpty()) {
                throw new ApplicationException(ERROR.INVALID_PARAM, "Department not found with Id " + request.getParentDepartmentId());
            }

            Department parentDepartment = checkParentOpt.get();
            Department department = Department.builder()
                    .name(request.getName())
                    .description(request.getDescription())
                    .detail(request.getDetail())
                    .isRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF)
                    .build();

            if (parentDepartment.getChildrenDepartments().contains(department)) {
                throw new ApplicationException(ERROR.INVALID_PARAM, "Already exist!");
            }

            List<Department> childDepartments = new ArrayList<>();
            department.setChildrenDepartments(childDepartments);
            department.setParentDepartment(parentDepartment);
            parentDepartment.getChildrenDepartments().add(department);
            department.setStatus(BaseStatus.ACTIVE);

            departmentRepository.save(department);

            List<Long> childrenDepartment = new ArrayList<>();
            for (Department d : childDepartments) {
                Long departmentId = d.getId();
                childrenDepartment.add(departmentId);
            }

            DepartmentDTO departmentDTO = DepartmentDTO.builder()
                    .name(department.getName())
                    .description(department.getDescription())
                    .detail(department.getDetail())
                    .childrenDepartmentList(childrenDepartment)
                    .isRoot(department.getIsRoot())
                    .build();

            if (department.getParentDepartment() == null) {
                departmentDTO.setParentDepartment("");
            }

            return new BaseResponseV2<>(departmentDTO);
        }

        Department department = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .detail(request.getDetail())
                .isRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF)
                .build();

        List<Department> childDepartments = new ArrayList<>();

        department.setChildrenDepartments(childDepartments);
        department.setParentDepartment(null);
        department.setStatus(BaseStatus.ACTIVE);

        departmentRepository.save(department);

        List<Long> childrenDepartments = new ArrayList<>();
        for (Department d : childDepartments) {
            Long departmentId = d.getId();
            childrenDepartments.add(departmentId);
        }

        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .name(department.getName())
                .description(department.getDescription())
                .detail(department.getDetail())
                .childrenDepartmentList(childrenDepartments)
                .isRoot(department.getIsRoot())
                .build();

        if (department.getParentDepartment() == null) {
            departmentDTO.setParentDepartment("");
        }

        return new BaseResponseV2<>(departmentDTO);
    }

    public BaseResponseV2<DepartmentDTO> update(long id, UpdateDepartmentRequest request) throws ApplicationException {
        validateUpdateRequest(request);

        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        if (optionalDepartment.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found department with ID : " + id);
        }

        Department existedDepartment = optionalDepartment.get();

        existedDepartment.setName(request.getName());
        existedDepartment.setDescription(request.getDescription());
        existedDepartment.setDetail(request.getDetail());

        departmentRepository.save(existedDepartment);

        List<Long> childrenDepartments = new ArrayList<>();
        for (Department d : existedDepartment.getChildrenDepartments()) {
            Long departmentId = d.getId();
            childrenDepartments.add(departmentId);
        }

        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .name(existedDepartment.getName())
                .description(existedDepartment.getDescription())
                .detail(existedDepartment.getDetail())
                .childrenDepartmentList(childrenDepartments)
                .isRoot(existedDepartment.getIsRoot())
                .build();

        return new BaseResponseV2<>(departmentDTO);
    }

    public BaseResponseV2 delete(long id) throws ApplicationException {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        if (optionalDepartment.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department not found");
        }

        Department department = optionalDepartment.get();

        /* check if department is root
           if root delete relationship with children department */
        if (department.getIsRoot() == 0) {
            department.getChildrenDepartments().remove(department);
        }

        // delete relationship with parent department if exist
        for (Department d : department.getChildrenDepartments()) {
            if (d.getParentDepartment() != null) {
                this.delete(d.getId());
            }
        }

        //delete relationship with employee if exist
        for (Employee e : department.getEmployeeList()) {
            DepartmentEmployee.DePk deId = new DepartmentEmployee.DePk(department.getId(), e.getId());
            departmentEmployeeRepository.deleteById(deId);
        }

        departmentRepository.deleteById(id);

        return new BaseResponseV2();
    }

    private void validateCreateRequest(CreateDepartmentRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department's name is empty");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department's description is empty");
        }

        if (StringUtils.isBlank(request.getDetail())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department's detail is empty");
        }

        if (!request.checkIsRoot() && request.getParentDepartmentId() == null) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Parent is blank");
        }

        if (request.checkIsRoot() && request.getParentDepartmentId() != null) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Level is invalid");
        }
    }

    private void validateUpdateRequest(UpdateDepartmentRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department's name is empty");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department's description is empty");
        }

        if (StringUtils.isBlank(request.getDetail())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department's detail is empty");
        }
    }
}
