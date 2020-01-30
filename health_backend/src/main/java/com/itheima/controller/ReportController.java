package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.utils.DateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @ClassName ReportController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年01月30日 15:10
 * @Version 1.0.0
*/
@RestController()
@RequestMapping("/report")
public class ReportController {

  @Reference
  private MemberService memberService;

  @GetMapping("/getMemberReport")
  public Result getMemberReport() throws Exception{

    //创建封装map
    Map<String,Object> map = new HashMap<>();
    //月份封装类
    List<String> months = new ArrayList<>();


    //格式化时间 年月
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.MONTH,-12);

    for(int i = 1; i <= 12; i++){
      calendar.add(Calendar.MONTH,1);
      Date time = calendar.getTime();
      //封装时间
      months.add(DateUtils.parseDate2String(time,"YYYY-MM"));
    }

    //会员数量封装类
    List<Integer> memberCount = memberService.getMemeberCountByMonth(months);

    map.put("months",months);
    map.put("memberCount",memberCount);

    return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
  }

}
