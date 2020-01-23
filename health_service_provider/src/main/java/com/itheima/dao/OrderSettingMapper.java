package com.itheima.dao;
import java.util.Date;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import javax.persistence.Table;
import java.util.List;
import java.util.Map;

@Table(name="t_ordersetting")
public interface OrderSettingMapper {

  //插入数据
  public void add(OrderSetting orderSetting);

  //根据日期更新可预约人数
  int updateByOrderDate(OrderSetting updated);

  //根据日期查询设置是否已经存在
  public long isExisByDate(@Param("orderDate") String orderDate);

  //根据传入时间段获取 数据
  List<OrderSetting> getOrderSettingByMonth(Map dateMap);

  void editNumberByDate(OrderSetting orderSetting);

  //根据传入时间段 查询
  OrderSetting findOneByOrderDate(@Param("orderDate")Date orderDate);

}
