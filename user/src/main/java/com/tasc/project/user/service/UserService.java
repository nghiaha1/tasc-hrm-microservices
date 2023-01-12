package com.tasc.project.user.service;

import com.tasc.entity.BaseStatus;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.model.ERROR;
import com.tasc.model.constans.MESSAGE;
import com.tasc.model.dto.employee.EmployeeDTO;
import com.tasc.model.dto.user.UserResDTO;
import com.tasc.project.user.connector.EmployeeConnector;
import com.tasc.project.user.entity.User;
import com.tasc.project.user.model.request.LoginRequest;
import com.tasc.project.user.model.request.RegisterRequest;
import com.tasc.project.user.repository.UserRepository;
import com.tasc.redis.dto.UserLoginDTO;
import com.tasc.redis.repository.UserLoginRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    EmployeeConnector employeeConnector;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public BaseResponseV2<User> register(RegisterRequest request) throws ApplicationException {
        validationRegisterRequest(request);

        BaseResponseV2<EmployeeDTO> employeeInfoResponse = employeeConnector.findById(request.getEmployeeId());

        if (!employeeInfoResponse.isSuccess()) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        EmployeeDTO employeeDTO = employeeInfoResponse.getData();

        if (employeeDTO == null) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        if (employeeDTO.getStatus() != BaseStatus.ACTIVE) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setStatus(BaseStatus.ACTIVE);
        user.setEmployee(employeeDTO.getFullName());

        userRepository.save(user);

        BaseResponseV2<EmployeeDTO> updateEmployeeInfoResponse = employeeConnector.updateUser(request.getEmployeeId(), user.getId());

        if (!updateEmployeeInfoResponse.isSuccess()) {
            throw new ApplicationException(ERROR.INVALID_PARAM);
        }

        return new BaseResponseV2<User>(user);
    }

    private void validationRegisterRequest(RegisterRequest request) throws ApplicationException {
        if (StringUtils.isBlank(request.getUsername())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Username is empty");
        }

        if (StringUtils.isBlank(request.getPassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password is empty");
        }

        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password not match");
        }

        if (StringUtils.isBlank(String.valueOf(request.getEmployeeId()))) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Employee is empty");
        }
    }

    public BaseResponseV2<UserLoginDTO> login(LoginRequest request) throws ApplicationException {
        Optional<User> optionalUser = userRepository.findUserByUsername(request.getUsername());

        if (optionalUser.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Username " + request.getUsername() + " not found");
        }

        User user = optionalUser.get();

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Password not match");
        }

        UserLoginDTO userLoginDTO = new UserLoginDTO();
        String token = UUID.randomUUID().toString();

        userLoginDTO.setToken(token);
        userLoginDTO.setUserId(user.getId());
        userLoginDTO.setTimeToLive(3600);
//        userLoginDTO.setRoleDTOS();

        userLoginRepository.save(userLoginDTO);

        return new BaseResponseV2<UserLoginDTO>(userLoginDTO);
    }

    public BaseResponseV2<UserResDTO> findById(long id) throws ApplicationException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ApplicationException(ERROR.INVALID_PARAM, "Not found user with Id: " + id);
        }

        User user = optionalUser.get();
        UserResDTO userResDTO = UserResDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .employee(user.getEmployee())
                .status(user.getStatus().code)
                .build();

        return new BaseResponseV2<UserResDTO>(userResDTO);
    }

    public void handleEventOrder(UserResDTO userResDTO){
        // handle event order

        if (userResDTO.getStatus() == MESSAGE.STATUS.CREATED){
            this.handleOrderEventCreated(userResDTO);
            return;
        }

        if (userResDTO.getStatus() == MESSAGE.STATUS.SUCCESS){
            this.handleOrderEventSuccess(userResDTO);
            return;
        }
        if (userResDTO.getStatus() == MESSAGE.STATUS.FAIL){
            this.handleOrderEventFail(userResDTO);
            return;
        }
    }

    public void handleOrderEventCreated(UserResDTO userResDTO){}
    public void handleOrderEventSuccess(UserResDTO userResDTO){}
    public void handleOrderEventFail(UserResDTO userResDTO){}
}
