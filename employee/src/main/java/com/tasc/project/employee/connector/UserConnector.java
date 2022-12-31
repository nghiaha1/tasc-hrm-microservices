package com.tasc.project.employee.connector;

import com.tasc.model.BaseResponseV2;
import com.tasc.model.dto.user.UserResDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-connector", url = "http://localhost:8082/api/v1/user")
public interface UserConnector {

    @GetMapping(path = "{id}")
    BaseResponseV2<UserResDTO> findUserById(@PathVariable long id);
}
