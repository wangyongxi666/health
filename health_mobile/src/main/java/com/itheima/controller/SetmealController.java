package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.POST;
import java.util.List;

/**
 * @ClassName SetmealController
 * @Description 移动端 查看套餐
 * @Author YongXi.Wang
 * @Date  2020年01月07日 17:32
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

  @Reference
  private SetmealService setmealService;

  @PostMapping("/getSetmeal")
  public Result getAllSetmeal(){
    try {
      List<Setmeal> list = setmealService.findAll();
      return new Result(true,MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.GET_SETMEAL_LIST_FAIL);
    }
  }

  @PostMapping("/findById")
  public Result findById(int id){
    try {
      Setmeal setmeal = setmealService.findById(id);
      return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
    }
  }

}
