package com.itheima.dao;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Insert;

import java.util.Map;

public interface SetmealMapper {

  public void addSetmealandCheckgroup(Map map);

  public void delAndCheckGroup(Integer setmealId);

  public void insertSelective(Setmeal setmeal);

  Page<Setmeal> findPage(@Param("queryString") String queryString);

  int deleteById(@Param("id")Integer id);

  List<Setmeal> select();

  Setmeal findById(@Param("id")Integer id);

  List<Setmeal> findAll();






}
