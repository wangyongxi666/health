package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberMapper;
import com.itheima.dao.OrderMapper;
import com.itheima.dao.OrderSettingMapper;
import com.itheima.dao.SetmealMapper;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.OrderSetting;
import com.itheima.pojo.Setmeal;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.pojo.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OrderServiceImpl
 * @Description 订单 服务实现类
 * @Author YongXi.Wang
 * @Date  2020年01月14日 9:45
 * @Version 1.0.0
*/
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderSettingMapper orderSettingMapper;

  @Autowired
  private MemberMapper memberMapper;

  @Autowired
  private OrderMapper orderMapper;

  @Autowired
  private SetmealMapper setmealMapper;

  @Override
  public Result submit(Map map) throws Exception {

    //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
    String orderDate = (String) map.get("orderDate");
    OrderSetting orderSetting = orderSettingMapper.findOneByOrderDate(DateUtils.parseString2Date(orderDate));

    if(orderSetting == null){
      return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
    }

    //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
    //已预约人数
    Integer reservations = orderSetting.getReservations();
    //可预约人数
    Integer number = orderSetting.getNumber();

    //当已预约人数大于等于可预约人数 则返回异常
    if(reservations >= number){
      return new Result(false,MessageConstant.ORDER_FULL);
    }

    //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
    //根据telephone 获取 memberId
    String telephone = (String) map.get("telephone");
    Member member = memberMapper.findOneByPhonenumber(telephone);

    if(member != null){

      //会员id
      Integer id = member.getId();
      //预约套餐id
      String setmealId = (String) map.get("setmealId");

      //根据会员id 预约时间 套餐id 查询是否已经预约过
      Order orderByFind = new Order(id,DateUtils.parseString2Date(orderDate),Integer.valueOf(setmealId));

      List<Order> byAll = orderMapper.findByAll(orderByFind);

      Order order = null;

      if(byAll != null && !byAll.isEmpty()){
        order = byAll.get(0);
      }

      //如果能查询出来，则证明已经预约过次套餐
      if(order != null){
        return new Result(false,MessageConstant.HAS_ORDERED);
      }

    }else{

      //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
      member = new Member();
      member.setName((String) map.get("name"));
      member.setSex((String) map.get("sex"));
      member.setIdCard(((String) map.get("idCard")));
      member.setPhoneNumber(((String) map.get("telephone")));

      memberMapper.add(member);

    }

    //5、预约成功，更新当日的已预约人数
    Order order = new Order();
    order.setMemberId(member.getId());
    order.setOrderDate(DateUtils.parseString2Date(orderDate));
    order.setOrderType((String)map.get("orderType"));
    order.setOrderStatus(Order.ORDERSTATUS_NO);
    order.setSetmealId(Integer.valueOf((String) map.get("setmealId")));

    //插入订单
    orderMapper.insert(order);

    //预约成功后 当天预约人数加一
    orderSetting.setReservations(orderSetting.getReservations() + 1);

    orderSettingMapper.updateByOrderDate(orderSetting);

    return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
  }

  @Override
  public Map findById(Integer id) throws Exception {

    Map resultMap = new HashMap();

    //根据订单id获取订单数据
    Order order = orderMapper.findById(id);

    //根据用户id获取用户对象
    Member member = memberMapper.findById(order.getMemberId());

    //根据套餐id获取套餐对象
    Setmeal setmeal = setmealMapper.findById(order.getSetmealId());

    //获取体检人姓名
    resultMap.put("member",member.getName());

    //获取体检套餐名称
    resultMap.put("setmeal",setmeal.getName());

    //获取体检日期
    if(order.getOrderDate() != null){
      resultMap.put("orderDate",DateUtils.parseDate2String(order.getOrderDate()));
    }

    //获取预约类型
    resultMap.put("orderType",order.getOrderType());

    return resultMap;
  }
}
