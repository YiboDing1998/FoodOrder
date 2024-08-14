package com.sky.service.impl;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.service.UserService;

import org.springframework.context.annotation.Bean;

public class UserServiceImpl implements UserService {

  @Override

  @Bean
  public User wxLogin(UserLoginDTO userLoginDTO) {
    return null;
  }
}
