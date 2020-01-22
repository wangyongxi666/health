package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.POST;
import java.util.*;

/**
 * @ClassName OrderSettingController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年01月04日 11:26
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

  @Reference
  private OrderSettingService orderSettingService;

  @PostMapping("/upload")
  public Result upload(@RequestParam("excelFile") MultipartFile excelFile){

    try {

      //解析读取的文件
      List<String[]> list = POIUtils.readExcel(excelFile);

      List<OrderSetting> orderSettings = new ArrayList<>();

      //循环取出数据
      for (String[] strings : list) {
        OrderSetting orderSetting = new OrderSetting();

        //封装数据
        orderSetting.setOrderDate(new Date(strings[0]));
        orderSetting.setNumber(Integer.valueOf(strings[1]));

        orderSettings.add(orderSetting);
      }

      orderSettingService.add(orderSettings);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
  }

  /**
   * 根据年月查询预约数据
  **/
  @PostMapping("/getOrderSettingByMonth")
  public Result getOrderSettingByMonth(String date){

    try {

      List<Map> orderSettingMap = orderSettingService.getOrderSettingByMonth(date);
      return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,orderSettingMap);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
    }

  }

  /**
   * 根据日期修改预约人数
  **/
  @PostMapping("/editNumberByDate")
  public Result editNumberByDate(@RequestBody OrderSetting orderSetting){

    try {
      orderSettingService.add(Arrays.asList(orderSetting));
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.ORDERSETTING_FAIL);
    }

    return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
  }

}
