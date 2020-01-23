package com.itheima.dao;

import java.util.List;
import java.util.Map;

public interface CheckgroupAndCheckitemMapper {


  Long selectCountByCheckitemId(Integer id);

  void add(Map map);

  List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);

  void deleteByCheckGroupId(int id);



}
