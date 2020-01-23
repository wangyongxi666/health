package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName ValidateCodeCodeController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年01月13日 20:54
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeCodeController {

  @Autowired
  private JedisPool jedisPool;

  /**
   *发送验证码
   */
  @PostMapping("/send4Order")
  public Result send4Order(String telephone){

    String vaCode;

    try {
      //生成四位验证码
      vaCode = ValidateCodeUtils.generateValidateCode4String(4);
      //发送验证码到手机
      SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,vaCode);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }

    //把验证码存入缓存中 (五分钟)
    jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER,300,vaCode);
    return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
  }

  /**
   * @Author YongXi.Wang
   * @Description //TODO 
   * @Date 2020/1/18 11:00
   * @Param [telephone] 
   * @return com.itheima.entity.Result
  **/
  @PostMapping("/send4Login")
  public Result send4Login(String telephone){

    if(telephone == null || telephone.isEmpty()){
      return new Result(false,"手机号不存在!");
    }

    //生成验证码
    String vaCode = ValidateCodeUtils.generateValidateCode4String(6);

    //发送验证码到手机上
    try {
      SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,vaCode);
    } catch (ClientException e) {
      e.printStackTrace();
      return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }

    //把验证码存入缓存中
    jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN,300,vaCode);

    return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);

  }

}
