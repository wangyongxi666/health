package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {

  /**
   * @Author YongXi.Wang
   * @Description 根据用户名查询用户对象
   * @Date 2020/1/28 18:22
   * @Param [username] 
   * @return com.itheima.pojo.User
  **/
  public User getUserByName(String username);
}
