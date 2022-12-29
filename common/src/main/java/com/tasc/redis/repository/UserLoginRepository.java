package com.tasc.redis.repository;

import com.tasc.redis.dto.UserLoginDTO;
import org.springframework.data.repository.CrudRepository;

public interface UserLoginRepository extends CrudRepository<UserLoginDTO, String> {

}
