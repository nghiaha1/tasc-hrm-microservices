package com.tasc.project.attendance.service;

import com.tasc.entity.AttendanceStatus;
import com.tasc.entity.BaseStatus;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.model.ERROR;
import com.tasc.model.dto.employee.EmployeeDTO;
import com.tasc.project.attendance.connector.EmployeeConnector;
import com.tasc.project.attendance.entity.Attendance;
import com.tasc.project.attendance.model.request.AttendanceRequest;
import com.tasc.project.attendance.repository.AttendanceRepository;
import com.tasc.project.attendance.search.AttendanceSpecification;
import com.tasc.project.attendance.search.SearchBody;
import com.tasc.project.attendance.search.SearchCriteria;
import com.tasc.project.attendance.search.SearchCriteriaOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    EmployeeConnector employeeConnector;

    public BaseResponseV2<Attendance> saveAttendance(AttendanceRequest request) throws ApplicationException {
        BaseResponseV2<EmployeeDTO> employeeResponseInfo = employeeConnector.findById(request.getEmployeeId());

        if (!employeeResponseInfo.isSuccess()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found employee with id: " + request.getEmployeeId());
        }

        EmployeeDTO employeeDTO = employeeResponseInfo.getData();

        if (employeeDTO == null) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        if (employeeDTO.getStatus() != BaseStatus.ACTIVE) {
            return new BaseResponseV2<>("Employee is not Active");
        }

        Attendance attendance = Attendance.builder()
                .employeeId(request.getEmployeeId())
                .attendanceStatus(request.getAttendanceStatus())
                .reason(request.getReason())
                .build();
        attendanceRepository.save(attendance);

        return new BaseResponseV2<Attendance>(attendance);
    }

    public BaseResponseV2<Attendance> getAttendanceByEmployeeId(long employeeId) throws ApplicationException {
        BaseResponseV2<EmployeeDTO> employeeResponseInfo = employeeConnector.findById(employeeId);

        if (!employeeResponseInfo.isSuccess()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found employee with id: " + employeeId);
        }

        EmployeeDTO employeeDTO = employeeResponseInfo.getData();

        if (employeeDTO == null) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        if (employeeDTO.getStatus() != BaseStatus.ACTIVE) {
            return new BaseResponseV2<>("Employee is not Active");
        }

        return new BaseResponseV2<>(attendanceRepository.findAttendanceByEmployeeId(employeeId));
    }

    public BaseResponseV2<Double> getAttendancePercentage(long employeeId, LocalDate startDate, LocalDate endDate) throws ApplicationException {
        BaseResponseV2<EmployeeDTO> employeeResponseInfo = employeeConnector.findById(employeeId);

        if (!employeeResponseInfo.isSuccess()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found employee with id: " + employeeId);
        }

        EmployeeDTO employeeDTO = employeeResponseInfo.getData();

        if (employeeDTO == null) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        if (employeeDTO.getStatus() != BaseStatus.ACTIVE) {
            return new BaseResponseV2<>("Employee is not Active");
        }

        List<Attendance> attendanceList = attendanceRepository.findAttendanceByEmployeeIdAndDateBetween(employeeId, startDate, endDate);

        if (attendanceList.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Attendance list is empty");
        }

        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        long presentDays = attendanceList.stream().
                filter(attendance -> attendance.getAttendanceStatus().equals(AttendanceStatus.PRESENT)).count();

        return new BaseResponseV2<>((double) (presentDays / totalDays) * 100);
    }

    public BaseResponseV2<List<Attendance>> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate) throws ApplicationException {
        List<Attendance> attendanceList = attendanceRepository.findAttendanceByDateBetween(startDate, endDate);

        if (!attendanceList.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Attendance list is empty");
        }

        return new BaseResponseV2<List<Attendance>>(attendanceList);
    }

    public BaseResponseV2<Map<String, Object>> findAll(SearchBody searchBody) throws ApplicationException {
        Specification specification = Specification.where(null);

        if (searchBody.getEmployeeId() > 0) {
            specification = specification.and(new AttendanceSpecification(
                    new SearchCriteria("employee", SearchCriteriaOperator.EQUALS, searchBody.getEmployeeId())));
        }

        if (searchBody.getStartDate() != null && searchBody.getStartDate().length() > 0) {
            specification = specification.and(new AttendanceSpecification(
                    new SearchCriteria("date", SearchCriteriaOperator.GREATER_THAN_OR_EQUALS, searchBody.getStartDate())));
        }

        if (searchBody.getEndDate() != null && searchBody.getEndDate().length() > 0) {
            specification = specification.and(new AttendanceSpecification(
                    new SearchCriteria("date", SearchCriteriaOperator.LESS_THAN_OR_EQUALS, searchBody.getEndDate())));
        }

        if (searchBody.getStatus() > -1) {
            specification = specification.and(new AttendanceSpecification(
                    new SearchCriteria("status", SearchCriteriaOperator.EQUALS, searchBody.getEndDate())));
        }

        Sort sort = Sort.by(Sort.Order.asc("id"));
        if (searchBody.getSort() != null && searchBody.getSort().length() > 0) {
            if (searchBody.getSort().contains("desc")) {
                sort = Sort.by(Sort.Order.desc("id"));
            }
        }

        Pageable pageable = PageRequest.of(searchBody.getPage() - 1, searchBody.getPageSize(), sort);
        Page<Attendance> attendancePage = attendanceRepository.findAll(specification, pageable);
        List<Attendance> attendanceList = attendancePage.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("content", attendanceList);
        response.put("currentPage", attendancePage.getNumber() + 1);
        response.put("totalItems", attendancePage.getTotalElements());
        response.put("totalPage", attendancePage.getTotalPages());

        return new BaseResponseV2<>(response);
    }
}
