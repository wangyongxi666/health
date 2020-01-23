package com.itheima.dao;

import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Date;


public interface OrderMapper {

  List<Order> findByAll(Order order);

  int insert(Order order);

  Order findById(@Param("id")Integer id);



}