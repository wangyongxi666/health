package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupMapper;
import com.itheima.dao.CheckgroupAndCheckitemMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CheckgroupServiceImpl
 * @Description 检测组服务实现层
 * @Author YongXi.Wang
 * @Date  2019年12月21日 11:35
 * @Version 1.0.0
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupMapper checkgroupMapper;

    @Autowired
    private CheckgroupAndCheckitemMapper checkgroupAndCheckitemMapper;

    @Override
    public void add(CheckGroup checkgroup, Integer[] checkItemIds) {

      //插入数据库
      checkgroupMapper.add(checkgroup);

      //返回自增id
      Integer id = checkgroup.getId();

      //绑定关系
      beLinked(id,checkItemIds);

    }

    /**
     * 绑定检查项与检测组的关系
    **/
    public void beLinked(int id,Integer[] checkItemIds){
      //把自增id和checkItemIds关联起来
      if(checkItemIds != null && checkItemIds.length > 0){
        for (Integer checkItemId : checkItemIds) {
          Map map = new HashMap<>();
          map.put("checkgroupId",id);
          map.put("checkitemId",checkItemId);
          checkgroupAndCheckitemMapper.add(map);
        }
      }
    }

  @Override
  public PageResult findPage(QueryPageBean queryPageBean) {

    Integer currentPage = queryPageBean.getCurrentPage();
    Integer pageSize = queryPageBean.getPageSize();
    String queryString = queryPageBean.getQueryString();

    //分页
    PageHelper.startPage(currentPage, pageSize);

    //查询
    Page<CheckGroup> checkGroups = checkgroupMapper.findByCondition(queryString);

    //封装
    PageResult pageResult = new PageResult(checkGroups.getTotal(),checkGroups.getResult());

    return pageResult;
  }

  @Override
  public CheckGroup findById(int id) {
    CheckGroup checkGroup = checkgroupMapper.findById(id);
    return checkGroup;
  }

  @Override
  public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
    //修改检查组数据
    checkgroupMapper.updateById(checkGroup);

    //删除关联关系
    checkgroupAndCheckitemMapper.deleteByCheckGroupId(checkGroup.getId());

    //重新建立关联关系
    beLinked(checkGroup.getId(),checkItemIds);
  }

  @Override
  public List<CheckGroup> findAll() {
    List<CheckGroup> all = checkgroupMapper.findAll();
    return all;
  }

  @Override
  public void delete(int id) {

    //删除关联关系
    checkgroupAndCheckitemMapper.deleteByCheckGroupId(id);

    //删除检查组
    checkgroupMapper.deleteById(id);
  }
}
