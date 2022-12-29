package com.tasc.project.user.listener;

import com.tasc.model.dto.user.UserResDTO;
import com.tasc.project.user.service.UserService;
import com.tasc.utils.JsonHelper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class RedisMessageSubscriber implements MessageListener {

    @Autowired
    UserService userService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = message.toString();

        UserResDTO userResDTO = JsonHelper.getObject(msg , UserResDTO.class);

        userService.handleEventOrder(userResDTO);

        log.info("USER-SERVICE order event created {}" , msg);
    }
}
