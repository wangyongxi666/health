package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberMapper;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MemberServiceImple
 * @Description 会员业务接口实现类
 * @Author YongXi.Wang
 * @Date  2020年01月18日 19:43
 * @Version 1.0.0
*/
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImple implements MemberService{

  @Autowired
  private MemberMapper memberMapper;

  @Override
  public Member findByTelephone(String telephone) {

    Member oneByPhonenumber = memberMapper.findOneByPhonenumber(telephone);

    return oneByPhonenumber;
  }

  @Override
  public void add(Member member) {

    //如果通过表单提交的注册数据 ，是有密码的，需要特殊处理
    String password = member.getPassword();
    if(password != null && !password.isEmpty()){
      password = MD5Utils.md5(password);
      member.setPassword(password);
    }

    if(member != null){
      memberMapper.add(member);
    }

  }

  @Override
  public List<Integer> getMemeberCountByMonth(List<String> months) {
    List<Integer> counts = new ArrayList<>();
    for (String month : months) {
      month = month + ".31";
      Integer count = memberMapper.getMemeberCountBeforeMonthMax(month);
      counts.add(count);
    }
    return counts;
  }
}
