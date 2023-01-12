package com.tasc.controller;

import com.tasc.model.BaseResponseV2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    public ResponseEntity<BaseResponseV2> createdResponse(BaseResponseV2 response){
        return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
    }
    public ResponseEntity<BaseResponseV2> createdResponse(BaseResponseV2 response, HttpStatus httpStatus){
        return new ResponseEntity<>(response , httpStatus);
    }

    public ResponseEntity<BaseResponseV2> createdResponse(HttpStatus httpStatus) {
        return new ResponseEntity<>(httpStatus);
    }
}
