package com.itheima.service;

import com.itheima.pojo.OrderSetting;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {

  //新增文件
  public void add(List<OrderSetting> orderSettings);

  //根据月份获取预约数据
  public List<Map> getOrderSettingByMonth(String date);


}
