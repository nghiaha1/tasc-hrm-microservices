package com.tasc.redis.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Data
@RedisHash("login")
public class UserLoginDTO {
    @Id
    private String token;

    private long userId;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long timeToLive;
}
