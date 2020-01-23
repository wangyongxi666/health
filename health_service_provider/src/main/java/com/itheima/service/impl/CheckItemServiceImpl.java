package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckItemMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName CheckItemServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2019年12月12日 21:40
 * @Version 1.0.0
*/

@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService{

  @Autowired
  private CheckItemMapper checkItemMapper;

  @Override
  public void add(CheckItem checkItem) {
    checkItemMapper.add(checkItem);
  }

  @Override
  public PageResult pageQuery(QueryPageBean queryPageBean) {
    Integer currentPage = queryPageBean.getCurrentPage();
    Integer pageSize = queryPageBean.getPageSize();
    String queryString = queryPageBean.getQueryString();

    //开启分页
    PageHelper.startPage(currentPage,pageSize);
    Page<CheckItem> checkItemList = checkItemMapper.selectByCondition(queryString);

    PageResult pageResult = new PageResult(checkItemList.getTotal(),checkItemList.getResult());

    return pageResult;
  }

  @Override
  public void deleteById(Integer id) {
    checkItemMapper.deleteById(id);
  }

  @Override
  public CheckItem selectById(Integer id) {
    return checkItemMapper.selectById(id);
  }

  @Override
  public void edit(CheckItem checkItem) {
    checkItemMapper.updateById(checkItem);
  }

  @Override
  public List<CheckItem> findAll() {
    return checkItemMapper.findAll();
  }


}
