package com.tasc.model.userauthen;

import lombok.Data;

@Data
public class UserDTO {
    private String token;
    private long userId;
}
