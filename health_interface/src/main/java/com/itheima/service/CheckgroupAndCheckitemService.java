package com.itheima.service;

import java.util.List;

public interface CheckgroupAndCheckitemService {

  /**
   * 根据CheckitemId查询关联数量
  **/
  public Long selectCountByCheckitemId(Integer id);

  List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId);
}
