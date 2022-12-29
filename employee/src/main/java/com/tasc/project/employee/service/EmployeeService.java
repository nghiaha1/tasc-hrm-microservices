package com.tasc.project.employee.service;

import com.tasc.entity.BaseStatus;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.model.ERROR;
import com.tasc.model.dto.employee.EmployeeDTO;
import com.tasc.project.employee.entity.Employee;
import com.tasc.project.employee.model.request.CreateEmployeeRequest;
import com.tasc.project.employee.repository.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ModelMapper modelMapper;

    public BaseResponseV2<EmployeeDTO> findById(long id) throws ApplicationException {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found employee with ID: " + id);
        }

        Employee employee = optionalEmployee.get();

        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .gender(employee.getGender())
                .dob(employee.getDob())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .salary(employee.getSalary())
                .bonus(employee.getBonus())
                .description(employee.getDescription())
                .detail(employee.getDetail())
//                .userId(employee.getUserId())
                .status(employee.getStatus())
                .build();

        return new BaseResponseV2<EmployeeDTO>(employeeDTO);
    }

    public BaseResponseV2<Page<Employee>> findAll(String status, int page, int pageSize) throws ApplicationException {
        if (page <= 0) {
            page = 1;
        }

        if (pageSize <= 0) {
            pageSize = 10;
        }

        Page<Employee> employeeList = employeeRepository.findEmployeesByStatus(status, PageRequest.of(page - 1, pageSize));

        if (employeeList.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Employee list is empty");
        }

        return new BaseResponseV2<Page<Employee>>(employeeList);
    }

    public BaseResponseV2<Employee> createdEmployee(CreateEmployeeRequest request) throws ApplicationException {
        invalidationEmployeeRequest(request);

        Employee employee = Employee.builder()
                .fullName(request.getFullName())
                .gender(request.getGender())
                .dob(request.getDob())
                .email(request.getEmail())
                .phone(request.getPhone())
                .description(request.getDescription())
                .detail(request.getDetail())
                .build();
        employee.setStatus(BaseStatus.ACTIVE);

        employeeRepository.save(employee);

        return new BaseResponseV2<Employee>(employee);
    }

    private void invalidationEmployeeRequest(CreateEmployeeRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getFullName())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Full name is empty");
        }

        if (StringUtils.isBlank(request.getGender())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Gender is empty");
        }

        if (StringUtils.isBlank(request.getDob().toString())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Date of birth is empty");
        }

        if (StringUtils.isBlank(request.getEmail())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Email is empty");
        }

        if (StringUtils.isBlank(request.getPhone())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Phone is empty");
        }

        if (StringUtils.isBlank(request.getDescription())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Description is empty");
        }

        if (StringUtils.isBlank(request.getDetail())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Detail is empty");
        }
    }
}