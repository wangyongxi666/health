package com.itheima.dao;

import com.itheima.pojo.User;

public interface UserMapper {

  public User findUserByUsername(String username);

}
