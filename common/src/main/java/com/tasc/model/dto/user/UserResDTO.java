package com.tasc.model.dto.user;

import com.tasc.entity.BaseStatus;
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

    private String role;

    private String employee;

    private BaseStatus status;
}
