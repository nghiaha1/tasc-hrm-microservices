package com.tasc.utils;


import com.tasc.model.userauthen.UserDTO;

public class ThreadLocalCollection {
    private static ThreadLocal<UserDTO> USER_LOGIN_INFO = new ThreadLocal<>();

    public static void putData(UserDTO data){
        USER_LOGIN_INFO.set(data);
    }

    public static UserDTO getUserActionLog(){
        return USER_LOGIN_INFO.get();
    }

    public static void removeData(){
        USER_LOGIN_INFO.remove();
    }
}
