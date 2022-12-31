package com.tasc.project.user.controller;

import com.tasc.controller.BaseController;
import com.tasc.model.ApplicationException;
import com.tasc.model.BaseResponseV2;
import com.tasc.project.user.model.request.LoginRequest;
import com.tasc.project.user.model.request.RegisterRequest;
import com.tasc.project.user.service.UserService;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<BaseResponseV2> register(@RequestBody RegisterRequest request) throws AppenderLoggingException {
        return createdResponse(userService.register(request));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<BaseResponseV2> login(@RequestBody LoginRequest request) throws ApplicationException {
        return createdResponse(userService.login(request));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BaseResponseV2> findById(@PathVariable long id) throws ApplicationException {
        return createdResponse(userService.findById(id));
    }
}
