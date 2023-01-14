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
import com.tasc.project.employee.repository.DepartmentEmployeeRepository;
import com.tasc.project.employee.repository.DepartmentRepository;
import com.tasc.project.employee.repository.EmployeeRepository;
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
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentEmployeeRepository departmentEmployeeRepository;

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

            List<Employee> employeeList = new ArrayList<>();

            for (int i = 0; i < request.getEmployeeIdList().size(); i++) {
                Optional<Employee> optionalEmployee = employeeRepository.findById(request.getEmployeeIdList().get(i));

                if (optionalEmployee.isEmpty()) {
                    throw new ApplicationException(ERROR.INVALID_PARAM, "Not found employee with ID : " + request.getEmployeeIdList().get(i));
                }
                Employee employee = optionalEmployee.get();

                employeeList.add(employee);
            }

            department.setEmployeeList(employeeList);

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
                    .parentDepartment(department.getParentDepartment().getName())
                    .childrenDepartmentList(childrenDepartment)
                    .isRoot(department.getIsRoot())
                    .build();

            List<Long> employeeIdList = new ArrayList<>();

            for (Employee employee : department.getEmployeeList()) {
                if (StringUtils.isNotBlank(String.valueOf(employee.getId()))) {
                    employeeIdList.add(employee.getId());
                    departmentDTO.setEmployeeList(employeeIdList);
                }
            }

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

        List<Employee> employeeList = new ArrayList<>();

        for (int i = 0; i < request.getEmployeeIdList().size(); i++) {
            Optional<Employee> optionalEmployee = employeeRepository.findById(request.getEmployeeIdList().get(i));

            if (optionalEmployee.isEmpty()) {
                throw new ApplicationException(ERROR.INVALID_PARAM, "Not found employee with ID : " + request.getEmployeeIdList().get(i));
            }
            Employee employee = optionalEmployee.get();

            employeeList.add(employee);
        }

        department.setEmployeeList(employeeList);

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
                .parentDepartment(department.getParentDepartment().getName())
                .childrenDepartmentList(childrenDepartment)
                .isRoot(department.getIsRoot())
                .build();

        List<Long> employeeIdList = new ArrayList<>();

        for (Employee employee : department.getEmployeeList()) {
            if (StringUtils.isNotBlank(String.valueOf(employee.getId()))) {
                employeeIdList.add(employee.getId());
                departmentDTO.setEmployeeList(employeeIdList);
            }
        }

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

    public BaseResponseV2 delete(long id) throws ApplicationException {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);

        if (optionalDepartment.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Department not found");
        }

        Department department = optionalDepartment.get();

        /* check if department is root
           if root delete relationship with children department */
        if (department.getIsRoot() == 0) {
            department.getChildrenDepartment().remove(department);
        }

        // delete relationship with parent department if exist
        for (Department d : department.getChildrenDepartment()) {
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
}
