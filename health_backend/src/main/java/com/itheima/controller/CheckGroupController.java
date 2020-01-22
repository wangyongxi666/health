package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import com.itheima.service.CheckgroupAndCheckitemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CheckGroupController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date 2019年12月21日 10:37
 * @Version 1.0.0
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

  @Reference
  private CheckGroupService checkGroupService;

  @Reference
  private CheckgroupAndCheckitemService checkgroupAndCheckitemService;

  @GetMapping("/findAll")
  public Result findAll(){
    try {
      List<CheckGroup> all = checkGroupService.findAll();
      return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,all);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
  }

  @PostMapping("/findPage")
  public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {

    PageResult pageResult = checkGroupService.findPage(queryPageBean);

    return pageResult;
  }

  @PostMapping("/add")
  public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
    Result result = null;

    try {
      checkGroupService.add(checkGroup, checkitemIds);
    } catch (Exception e) {
      e.printStackTrace();
      result = new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
    }
    return result = new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
  }

  @GetMapping("/findById")
  public Result findById(int id) {

    Result result = null;

    try {
      CheckGroup checkGroup = checkGroupService.findById(id);
      result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
    } catch (Exception e) {
      e.printStackTrace();
      result = new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
    }

    return result;
  }

  /**
   * 根据CheckGroupId 查询 CheckItemIds
   **/
  @GetMapping("/findCheckItemIdsByCheckGroupId")
  public Result findCheckItemIdsByCheckGroupId(int id) {

    Result result = null;

    try {
      List<Integer> ids = checkgroupAndCheckitemService.findCheckItemIdsByCheckGroupId(id);
      result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, ids);
    } catch (Exception e) {
      e.printStackTrace();
      result = new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
    }

    return result;
  }

  @PostMapping("/edit")
  public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {

    Result result = null;

    try {
      checkGroupService.edit(checkGroup, checkitemIds);
      result = new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    } catch (Exception e) {
      e.printStackTrace();
      result = new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
    }

    return result;
  }

  @GetMapping("/delete")
  public Result delete(int id){
    try {
      checkGroupService.delete(id);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
    }
    return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
  }

}
