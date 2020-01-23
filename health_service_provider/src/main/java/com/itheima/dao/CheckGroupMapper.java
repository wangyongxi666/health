package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface CheckGroupMapper {

    void add(CheckGroup checkGroup);

    Page<CheckGroup> findByCondition(@Param("queryString") String queryString);

    CheckGroup findById(@Param("id") int id);

    void updateById(CheckGroup checkGroup);

    List<CheckGroup> findAll();

    int deleteById(@Param("id")Integer id);

    List<CheckGroup> findCheckGroupById(Integer id);
}
