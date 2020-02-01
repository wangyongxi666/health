package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

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

  /**
   * @Author YongXi.Wang
   * @Description 根据月份列表查询当月会员数量
   * @Date 2020/1/30 16:17
   * @Param [months] 
   * @return java.util.List<java.lang.String>
  **/
  List<Integer> getMemeberCountByMonth(List<String> months);

}
