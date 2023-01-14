package com.tasc.project.attendance.service;

import com.tasc.entity.AttendanceStatus;
import com.tasc.entity.BaseStatus;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.model.ERROR;
import com.tasc.model.dto.AttendanceDTO;
import com.tasc.model.dto.employee.EmployeeDTO;
import com.tasc.project.attendance.connector.EmployeeConnector;
import com.tasc.project.attendance.entity.Attendance;
import com.tasc.project.attendance.model.request.AttendanceRequest;
import com.tasc.project.attendance.repository.AttendanceRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    EmployeeConnector employeeConnector;

    public BaseResponseV2<AttendanceDTO> saveAttendance(AttendanceRequest request) throws ApplicationException {
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
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .build();
        
        if (StringUtils.isBlank(String.valueOf(request.getEmployeeId())) ||
                request.getCheckIn() == null ||
                request.getCheckOut() == null) {
            attendance.setAttendanceStatus(AttendanceStatus.ABSENT);
        }
        
        attendance.setAttendanceStatus(AttendanceStatus.PRESENT);
        
        attendanceRepository.save(attendance);

        AttendanceDTO attendanceDTO = AttendanceDTO.builder()
                .id(attendance.getId())
                .employeeId(attendance.getEmployeeId())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .attendanceStatus(attendance.getAttendanceStatus())
                .build();

        return new BaseResponseV2<AttendanceDTO>(attendanceDTO);
    }

    public BaseResponseV2<Attendance> getAttendanceByEmployeeId(long employeeId) throws ApplicationException {

        checkEmployeeByEmployeeId(employeeId);

        return new BaseResponseV2<>(attendanceRepository.findAttendanceByEmployeeId(employeeId));
    }

    public BaseResponseV2<List<AttendanceDTO>> findAll() throws ApplicationException {
        List<Attendance> attendanceList = attendanceRepository.findAll();
        List<AttendanceDTO> attendanceDTOS = new ArrayList<>();

        for (Attendance attendance : attendanceList) {
            AttendanceDTO attendanceDTO = AttendanceDTO.builder()
                    .id(attendance.getId())
                    .employeeId(attendance.getEmployeeId())
                    .checkIn(attendance.getCheckIn())
                    .checkOut(attendance.getCheckOut())
                    .attendanceStatus(attendance.getAttendanceStatus())
                    .build();
            attendanceDTOS.add(attendanceDTO);
        }

        return new BaseResponseV2<List<AttendanceDTO>>(attendanceDTOS);
    }

    private void checkEmployeeByEmployeeId(long employeeId) throws ApplicationException {
        BaseResponseV2<EmployeeDTO> employeeResponseInfo = employeeConnector.findById(employeeId);

        if (!employeeResponseInfo.isSuccess()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found employee with id: " + employeeId);
        }

        EmployeeDTO employeeDTO = employeeResponseInfo.getData();

        if (employeeDTO == null) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        if (employeeDTO.getStatus() != BaseStatus.ACTIVE) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Employee is not ACTIVE");
        }
    }

    public BaseResponseV2<AttendanceDTO> findById(long id) throws ApplicationException {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);

        if (optionalAttendance.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found attendance with ID: " + id);
        }

        Attendance attendance = optionalAttendance.get();

        AttendanceDTO attendanceDTO = AttendanceDTO.builder()
                .id(attendance.getId())
                .employeeId(attendance.getEmployeeId())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .attendanceStatus(attendance.getAttendanceStatus())
                .build();

        return new BaseResponseV2<>(attendanceDTO);
    }

    public BaseResponseV2 delete(long id) throws ApplicationException {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);

        if (optionalAttendance.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found attendance with ID: " + id);
        }

        attendanceRepository.deleteById(id);

        return new BaseResponseV2("Delete Success");
    }

    public BaseResponseV2<List<AttendanceDTO>> findByEmployeeAndDateRange(long employeeId, LocalDate startDate, LocalDate endDate) throws ApplicationException {
        List<Attendance> attendanceRecords = attendanceRepository.findAttendanceByEmployeeIdAndDateBetween(employeeId, startDate, endDate);

        if (attendanceRecords.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "List null");
        }

        List<AttendanceDTO> attendanceDTOS = new ArrayList<>();

        for (Attendance attendance : attendanceRecords) {
            AttendanceDTO attendanceDTO = AttendanceDTO.builder()
                    .id(attendance.getId())
                    .employeeId(attendance.getEmployeeId())
                    .checkIn(attendance.getCheckIn())
                    .checkOut(attendance.getCheckOut())
                    .attendanceStatus(attendance.getAttendanceStatus())
                    .build();
            attendanceDTOS.add(attendanceDTO);
        }

        return new BaseResponseV2<>(attendanceDTOS);

    }
}
