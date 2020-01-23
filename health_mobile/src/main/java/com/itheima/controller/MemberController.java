package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.http.HttpResponse;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName MemberController
 * @Description 会员中心
 * @Author YongXi.Wang
 * @Date 2020年01月18日 10:33
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/member")
public class MemberController {

  @Autowired
  private JedisPool jedisPool;

  @Reference
  private MemberService memberService;

  @PostMapping("/login")
  public Result login(HttpServletResponse response, @RequestBody Map map) {

    //检验传入的验证码是否正确
    //取出传入的手机号
    String telephone = (String) map.get("telephone");
    //取出传入的验证码
    String validateCode = (String) map.get("validateCode");
    //取出缓存中的验证码
    String s = telephone + RedisMessageConstant.SENDTYPE_LOGIN;
    String validateCodeFromRedis = jedisPool.getResource().get(s);
    //比对传入的验证码和缓存中的验证码
    if (validateCode != null && validateCodeFromRedis != null && validateCode.equals(validateCodeFromRedis)) {

      //如果验证码输入正确，则查询该用户是否是会员
      Member member = memberService.findByTelephone(telephone);

      //如果查询不到会员信息，则自动注册会员
      if (member == null) {
        member.setPhoneNumber(telephone);
        member.setRegTime(new Date());
        memberService.add(member);
      }

      //把该会员信息保存到cookie中
      Cookie cookie = new Cookie("login_member_telephone", telephone);
      cookie.setPath("/");
      cookie.setMaxAge(60 * 60 * 24 * 30);
      response.addCookie(cookie);

      //把该会员信息保存到redis中
      String memberStr = JSON.toJSON(member).toString();
      jedisPool.getResource().setex(telephone, 60 * 30, memberStr);
      return new Result(true, MessageConstant.LOGIN_SUCCESS);

    } else {
      return new Result(false, MessageConstant.VALIDATECODE_ERROR);
    }
  }

}
