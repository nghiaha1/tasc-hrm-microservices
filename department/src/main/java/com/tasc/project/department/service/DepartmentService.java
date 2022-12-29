package com.tasc.project.department.service;

import com.tasc.entity.BaseStatus;
import com.tasc.entity.Constant;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.model.ERROR;
import com.tasc.model.dto.department.DepartmentDTO;
import com.tasc.model.dto.employee.EmployeeDTO;
import com.tasc.project.department.connector.EmployeeConnector;
import com.tasc.project.department.entity.Department;
import com.tasc.project.department.model.request.CreateDepartmentRequest;
import com.tasc.project.department.repository.DepartmentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    EmployeeConnector employeeConnector;

    public BaseResponseV2<DepartmentDTO> create(CreateDepartmentRequest request) throws ApplicationException {
        validateRequest(request);

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

            if (parentDepartment.getChildrenDepartment().contains(department)) {
                throw new ApplicationException(ERROR.INVALID_PARAM, "Already exist!");
            }

            List<Department> childDepartments = new ArrayList<>();
            department.setChildrenDepartment(childDepartments);
            department.setParentDepartment(parentDepartment);
            parentDepartment.getChildrenDepartment().add(department);
            department.setStatus(BaseStatus.ACTIVE);

            List<String> employeeList = new ArrayList<>();

            for (int i = 0; i < request.getEmployeeIdList().size(); i++) {
                BaseResponseV2<EmployeeDTO> employeeDTOResponse = employeeConnector.findEmployeeById(request.getEmployeeIdList().get(i));
                if (!employeeDTOResponse.isSuccess()) {
                    throw new ApplicationException(ERROR.INVALID_PARAM);
                }

                EmployeeDTO employeeDTO = employeeDTOResponse.getData();

                if (employeeDTO == null) {
                    throw new ApplicationException(ERROR.INVALID_PARAM);
                }

                String employee = employeeDTO.getFullName();
                employeeList.add(employee);
            }

            department.setEmployeeList(employeeList);

            departmentRepository.save(department);

            List<String> childrenDepartment = new ArrayList<>();
            for (Department d: childDepartments) {
                String departmentName = d.getName();
                childrenDepartment.add(departmentName);
            }

            DepartmentDTO departmentDTO = DepartmentDTO.builder()
                    .name(department.getName())
                    .description(department.getDescription())
                    .detail(department.getDetail())
                    .employeeList(department.getEmployeeList())
                    .parentDepartment(department.getParentDepartment().getName())
                    .childrenDepartmentList(childrenDepartment)
                    .isRoot(department.getIsRoot())
                    .build();

            return new BaseResponseV2<DepartmentDTO>(departmentDTO);
        }

        Department department = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .detail(request.getDetail())
                .isRoot(request.checkIsRoot() ? Constant.ONOFF.ON : Constant.ONOFF.OFF)
                .build();

        List<Department> childDepartments = new ArrayList<>();

        department.setChildrenDepartment(childDepartments);
        department.setParentDepartment(null);
        department.setStatus(BaseStatus.ACTIVE);

        List<String> employeeList = new ArrayList<>();

        for (int i = 0; i < request.getEmployeeIdList().size(); i++) {
            BaseResponseV2<EmployeeDTO> employeeDTOResponse = employeeConnector.findEmployeeById(request.getEmployeeIdList().get(i));
            if (!employeeDTOResponse.isSuccess()) {
                throw new ApplicationException(ERROR.INVALID_PARAM);
            }

            EmployeeDTO employeeDTO = employeeDTOResponse.getData();

            if (employeeDTO == null) {
                throw new ApplicationException(ERROR.INVALID_PARAM);
            }

            String employee = employeeDTO.getFullName();
            employeeList.add(employee);
        }

        department.setEmployeeList(employeeList);

        departmentRepository.save(department);

        List<String> childrenDepartment = new ArrayList<>();
        for (Department d: childDepartments) {
            String departmentName = d.getName();
            childrenDepartment.add(departmentName);
        }

        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .name(department.getName())
                .description(department.getDescription())
                .detail(department.getDetail())
                .employeeList(department.getEmployeeList())
                .parentDepartment(department.getParentDepartment().getName())
                .childrenDepartmentList(childrenDepartment)
                .isRoot(department.getIsRoot())
                .build();

        return new BaseResponseV2<DepartmentDTO>(departmentDTO);
    }

    private void validateRequest(CreateDepartmentRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getName())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department's name is empty");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department's description is empty");
        }

        if (StringUtils.isBlank(request.getDetail())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department's detail is empty");
        }

        if (request.getEmployeeIdList().isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department's employee list is empty");
        }

        if (!request.checkIsRoot() && request.getParentDepartmentId() == null) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Parent is blank");
        }

        if (request.checkIsRoot() && request.getParentDepartmentId() != null) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Level is invalid");
        }
    }


}
