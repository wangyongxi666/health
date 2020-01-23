package com.itheima.dao;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

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

}
