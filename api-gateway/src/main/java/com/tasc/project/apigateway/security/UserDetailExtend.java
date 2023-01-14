package com.tasc.project.apigateway.security;


import com.tasc.redis.dto.UserLoginDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class UserDetailExtend implements UserDetails {
    private long userId;

    private String role;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailExtend(){

    }

//    UserLoginDTO

//    public UserDetailExtend(UserLoginDTO userLoginDTO){
//        this.userId = userLoginDTO.getUserId();
//        this.role = userLoginDTO.getRole();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return "TASS";
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDetailExtend(UserLoginDTO userLoginDTO) {
        userId = userLoginDTO.getUserId();
        role = userLoginDTO.getRole();

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        authorities = new ArrayList<SimpleGrantedAuthority>();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userLoginDTO.getRole());

        simpleGrantedAuthorities.add(authority);

        authorities = simpleGrantedAuthorities;
    }
}
