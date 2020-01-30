package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SetmealService
 * @Description 检查套餐 服务
 * @Author YongXi.Wang
 * @Date  2019年12月27日 19:39
 * @Version 1.0.0
*/
public interface SetmealService {

  /**
   * 添加检查套餐
  **/
  public void add(Setmeal setmeal, Integer[] checkGroupIds);

  /**
   * 分页查询
  **/
  public PageResult findPage(QueryPageBean queryPageBean);

  /**
   * 删除套餐
  **/
  void delete(int id);

  /**
   * 获取检查套餐列表
  **/
  List<Setmeal> findAll();

  /**
   * 根据id获取检查套餐
  **/
  Setmeal findById(int id);

  /**
   * @Author YongXi.Wang
   * @Description 查询套餐占比
   * @Date 2020/1/30 21:09
   * @Param  
   * @return 
  **/
  List<Map<String,Object>> findSetmealCount();


}
