package com.tasc.project.apigateway.model;

import com.tasc.project.apigateway.security.UserDetailExtend;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;

public class TassUserAuthentication extends UsernamePasswordAuthenticationToken {

    public TassUserAuthentication(UserDetailExtend userDetailExtend  ) {
        super(userDetailExtend, null , new ArrayList<>());
    }
}
