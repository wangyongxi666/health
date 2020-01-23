package com.itheima.dao;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.itheima.pojo.CheckItem;

public interface CheckItemMapper {

  public void add(CheckItem checkItem);

  public Page<CheckItem> selectByCondition(@Param("queryString") String queryString);

  public void deleteById(Integer id);

  int updateById(CheckItem checkItem);

  CheckItem selectById(@Param("id")Integer id);

  List<CheckItem> findAll();

  List<CheckItem> findCheckItemById(Integer id);

}
