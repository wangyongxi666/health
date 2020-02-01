package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberMapper;
import com.itheima.dao.OrderMapper;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @ClassName ReportServiceImpl
 * @Description 运营数据统计服务
 * @Author YongXi.Wang
 * @Date  2020年01月31日 12:21
 * @Version 1.0.0
*/
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService{

  @Autowired
  private MemberMapper memberMapper;

  @Autowired
  private OrderMapper orderMapper;

  @Override
  public Map<String, Object> getBusinessReportData() throws Exception {

    Map<String,Object> resultMap = new HashMap<>();

    //今天日期
    String today = DateUtils.parseDate2String(DateUtils.getToday());
    //今日新增会员数
    Integer todayNewMember = memberMapper.findMemberCountByDate(today);
    //总会员数
    Integer totalMemberCount = memberMapper.findMemberTotalCount();
    //本周新增会员
    String monday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
    Integer thisWeekNewMember = memberMapper.findMemberBeforeDate(monday);
    //本月新增会员数
    String firstMonthDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
    Integer thisMonthNewMember = memberMapper.findMemberBeforeDate(firstMonthDay);
    //今日预约数
    Integer todayOrderNumber = orderMapper.findOrderCountByDate(today);
    //本周预约数
    Integer thisWeekOrderNumber = orderMapper.findOrderCountAfterDate(monday);
    //本月预约数
    Integer thisMonthOrderNumber = orderMapper.findOrderCountAfterDate(firstMonthDay);
    //今日到诊数
    Integer todayVisitsNumber = orderMapper.findVisitsCountByDate(today);
    //本周到诊数
    Integer thisWeekVisitsNumber = orderMapper.findVisitsCountAfterDate(monday);
    //本月到诊数
    Integer thisMonthVisitsNumber = orderMapper.findVisitsCountAfterDate(firstMonthDay);
    //热门套餐（取前4）
    List<Map> hotSetmeal = orderMapper.findHotSetmeal();

    resultMap.put("reportDate",today);
    resultMap.put("todayNewMember",todayNewMember);
    resultMap.put("totalMember",totalMemberCount);
    resultMap.put("thisWeekNewMember",thisWeekNewMember);
    resultMap.put("thisMonthNewMember",thisMonthNewMember);
    resultMap.put("todayOrderNumber",todayOrderNumber);
    resultMap.put("thisWeekOrderNumber",thisWeekOrderNumber);
    resultMap.put("thisMonthOrderNumber",thisMonthOrderNumber);
    resultMap.put("todayVisitsNumber",todayVisitsNumber);
    resultMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
    resultMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
    resultMap.put("hotSetmeal",hotSetmeal);

    return resultMap;
  }

}
