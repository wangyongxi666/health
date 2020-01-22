package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @Author YongXi.Wang
 * @Description 预约项目服务
 * @Date 2019/12/12 21:38
 * @Param
 * @return
**/

public interface CheckItemService {

  public void add(CheckItem checkItem);

  public PageResult pageQuery(QueryPageBean queryPageBean);

  public void deleteById(Integer id);

  CheckItem selectById(Integer id);

  void edit(CheckItem checkItem);

  List<CheckItem> findAll();
}
