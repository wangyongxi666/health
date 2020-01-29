package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionMapper;
import com.itheima.dao.RoleMapper;
import com.itheima.dao.UserMapper;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @ClassName UserServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年01月28日 20:56
 * @Version 1.0.0
*/
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService{

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private RoleMapper roleMapper;

  @Autowired
  private PermissionMapper permissionMapper;

  @Override
  public User getUserByName(String username) {

    //根据用户名查询用户数据
    User user = userMapper.findUserByUsername(username);

    Integer userId = user.getId();

    //根据用户id查询角色列表
    Set<Role> roles = roleMapper.findRoleByUserId(userId);

    for (Role role : roles) {

      Integer roleId = role.getId();

      //根据角色id查询权限
      Set<Permission> permissions = permissionMapper.findPermissionByRoleId(roleId);

      //装载权限数据
      role.setPermissions(permissions);

    }

    //装载角色数据
    user.setRoles(roles);

    return user;
  }
}
