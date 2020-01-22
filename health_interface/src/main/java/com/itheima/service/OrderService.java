package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map; /**
 * @Author YongXi.Wang
 * @Description 订单服务层
 * @Date 2020/1/14 9:45
 * @Param  
 * @return 
**/
public interface OrderService {
  
  /**
   * 提交预约数据
  **/
  Result submit(Map map) throws Exception;

  /**
   * 根据id查询订单
  **/
  Map findById(Integer id) throws Exception;
}
