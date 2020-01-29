package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.CheckgroupAndCheckitemMapper;
import com.itheima.service.CheckgroupAndCheckitemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CheckgroupAndCheckitemServiceImpl
 * @Description 中间表
 * @Author YongXi.Wang
 * @Date  2019年12月18日 20:09
 * @Version 1.0.0
*/
@Service(interfaceClass = CheckgroupAndCheckitemService.class)
@Transactional
public class CheckgroupAndCheckitemServiceImpl implements CheckgroupAndCheckitemService {

  @Autowired
  private CheckgroupAndCheckitemMapper checkgroupAndCheckitemMapper;

  @Override
  public Long selectCountByCheckitemId(Integer id) {

    if(id == null){
      return null;
    }

    return checkgroupAndCheckitemMapper.selectCountByCheckitemId(id);
  }

  @Override
  public List<Integer> findCheckItemIdsByCheckGroupId(int checkGroupId) {
    return checkgroupAndCheckitemMapper.findCheckItemIdsByCheckGroupId(checkGroupId);
  }
}
