package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingMapper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName OrderSettingServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年01月04日 13:25
 * @Version 1.0.0
*/
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

  @Autowired
  private OrderSettingMapper orderSettingMapper;

  @Override
  public void add(List<OrderSetting> orderSettings){

    for (OrderSetting orderSetting : orderSettings) {

      //检查当天是否已进行过设置
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
      String formatDate = simpleDateFormat.format(orderSetting.getOrderDate());
      long exis = orderSettingMapper.isExisByDate(formatDate);
      System.out.println(exis);
      if(exis > 0){
        //如果是则走修改
        orderSettingMapper.updateByOrderDate(orderSetting);
      }else {
        //如果否则走新增
        orderSettingMapper.add(orderSetting);
      }
    }

  }

  @Override
  public List<Map> getOrderSettingByMonth(String date) {

    //封装参数
    String begin = date + "-1";
    String end = date + "-31";

    Map dateMap = new HashMap();

    dateMap.put("begin",begin);
    dateMap.put("end",end);

    List<OrderSetting> list = orderSettingMapper.getOrderSettingByMonth(dateMap);

    List<Map> resultList = new ArrayList<>();

    if(list != null && list.size() > 0){
      for (OrderSetting orderSetting : list) {

        Map<String,Object> resultMap = new HashMap<>();

        resultMap.put("date",orderSetting.getOrderDate().getDate());
        resultMap.put("number",orderSetting.getNumber());
        resultMap.put("reservations",orderSetting.getReservations());

        resultList.add(resultMap);

      }
    }

    return resultList;
  }

}
