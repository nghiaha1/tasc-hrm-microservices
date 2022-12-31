package com.tasc.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResDTO implements Serializable {

    private long userId;

    private String username;

    private String employee;

    private int status;
}
