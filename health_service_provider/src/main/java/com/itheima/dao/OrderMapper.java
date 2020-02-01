package com.itheima.dao;

import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Date;
import java.util.Map;


public interface OrderMapper {

  List<Order> findByAll(Order order);

  int insert(Order order);

  Order findById(@Param("id")Integer id);

  public Map findById4Detail(Integer id);
  public Integer findOrderCountByDate(String date);
  public Integer findOrderCountAfterDate(String date);
  public Integer findVisitsCountByDate(String date);
  public Integer findVisitsCountAfterDate(String date);
  public List<Map> findHotSetmeal();
}