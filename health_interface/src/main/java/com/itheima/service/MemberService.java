package com.itheima.service;

import com.itheima.pojo.Member;

/**
 * @ClassName MemberService
 * @Description 会员业务接口
 * @Author YongXi.Wang
 * @Date  2020年01月18日 19:40
 * @Version 1.0.0
*/
public interface MemberService {

  /**
   * 根据手机账号查找会员信息
  **/
  Member findByTelephone(String telephone);

  /**
   * 添加会员信息
   **/
  void add(Member member);
}
