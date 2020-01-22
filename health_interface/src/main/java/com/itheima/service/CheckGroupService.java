package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
  /**
   * 新建检测组
  **/
  public void add(CheckGroup checkGroup, Integer[] checkItemIds);

  /**
   * 分页查询检测组
  **/
  public PageResult findPage(QueryPageBean queryPageBean);

  /**
   * 根据id查询检查组
  **/
  public CheckGroup findById(int id);

  /**
   * 修改检查组
  **/
  void edit(CheckGroup checkGroup, Integer[] checkItemIds);

  /**
   * 查询列表
  **/
  List<CheckGroup> findAll();

  /**
   * 删除
  **/
  void delete(int id);
}
