package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MemberMapper {

  /**
   * 根据手机号查询会员信息
  **/
  Member findOneByPhonenumber(@Param("phonenumber")String phonenumber);

  /**
   * 新增会员
  **/
  void add(Member member);

  Member findById(@Param("id")Integer id);

  /**
   * @Author YongXi.Wang
   * @Description 根据传入时间进行统计会员数量
   * @Date 2020/1/30 16:26
   * @Param [months] 
   * @return java.lang.Integer
  **/
  Integer getMemeberCountBeforeMonthMax(String month);
}
