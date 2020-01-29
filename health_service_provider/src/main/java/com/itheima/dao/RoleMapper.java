package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.List;
import java.util.Set;

public interface RoleMapper {

  /**
   * @Author YongXi.Wang
   * @Description 根据userId查询该用户关联的角色
   * @Date 2020/1/28 21:46
   * @Param [userId] 
   * @return com.itheima.pojo.Role
  **/
  public Set<Role> findRoleByUserId(Integer userId);
}
