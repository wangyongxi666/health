package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author YongXi.Wang
 * @Description 自定义登陆权限校验逻辑
 * @Date 2020/1/28 18:25
 * @Param  
 * @return
**/
@Component
public class SpringSecurityUserService implements UserDetailsService {

  @Reference
  private UserService userService;

  /**
   * @Author YongXi.Wang
   * @Description 查询用户信息
   * @Date 2020/1/28 18:27
   * @Param [s]
   * @return org.springframework.security.core.userdetails.UserDetails
  **/
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    //根据username查询用户
    User user = userService.getUserByName(username);

    if(user == null){
      return null;
    }

    List<GrantedAuthority> list = new ArrayList();

    Set<Role> roles = user.getRoles();

    //给用户授予角色
    for (Role role : roles) {
      list.add(new SimpleGrantedAuthority(role.getKeyword()));
      //给用户授予权限
      Set<Permission> permissions = role.getPermissions();
      for (Permission permission : permissions) {
        list.add(new SimpleGrantedAuthority(permission.getKeyword()));
      }
    }

    System.out.println(list);

    org.springframework.security.core.userdetails.User securityUser
            = new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);

    return securityUser;
  }
}
