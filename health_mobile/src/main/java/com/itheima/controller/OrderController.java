package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @ClassName ＯrderController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年01月13日 20:29
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private JedisPool jedisPool;

  @Reference
  private OrderService orderService;

  @PostMapping("/submit")
  public Result submit(@RequestBody Map map){

    //获取传入的验证码
    String validateCode = (String)(map.get("validateCode"));

    //获取缓存中的key
    String key = map.get("telephone") + RedisMessageConstant.SENDTYPE_ORDER;

    //获取缓存中的验证码
    String validateCodeFromRedis = jedisPool.getResource().get(key);

    //比对验证码
    if(validateCode != null && validateCodeFromRedis != null && validateCodeFromRedis.equals(validateCode)){

      //调用业务逻辑 进行预约提交
      map.put("orderType", Order.ORDERTYPE_WEIXIN); //设置预约分类，微信预约 、 电话预约

      Result result = null;

      try {
        result = orderService.submit(map);
      } catch (Exception e) {

        e.printStackTrace();
        return result;
      }

      //返回true则预约成功
      if(result.isFlag()){
        //为用户发送预约时间的短信
        try {
          SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,(String) map.get("telephone"),(String) map.get("orderDate"));
        } catch (ClientException e) {
          e.printStackTrace();
        }
      }

      return result;

    }else{
      return new Result(false, MessageConstant.VALIDATECODE_ERROR);
    }

  }

  @PostMapping("/findById")
  public Result findById(Integer id){
    try {
      Map map = orderService.findById(id);
      return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);

    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
    }
  }
}
