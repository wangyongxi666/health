package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年01月30日 0:14
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/user")
public class UserController {

  @GetMapping("/getUsername")
  public Result getUsername(){

    //使用security框架备份的用户数据 取得用户名
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if(user != null){
      String username = user.getUsername();
      return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
    }

    return new Result(false,MessageConstant.GET_USERNAME_FAIL);
  }

}
