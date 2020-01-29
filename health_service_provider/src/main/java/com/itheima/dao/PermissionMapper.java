package com.itheima.dao;

import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;

import java.util.List;
import java.util.Set;

public interface PermissionMapper {

  /**
   * @Author YongXi.Wang
   * @Description 根据角色id查询 权限列表
   * @Date 2020/1/28 22:18
   * @Param [roleId]
   * @return List<Permission>
  **/
  public Set<Permission> findPermissionByRoleId(Integer roleId);

}
