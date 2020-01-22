package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import com.itheima.service.CheckgroupAndCheckitemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName com.itheima.com.itheima.controller.CheckItemController
 * @Description 预约项目控制层
 * @Author YongXi.Wang
 * @Date  2019年12月12日 21:02
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

  @Reference
  private CheckItemService checkItemService;

  @Reference
  private CheckgroupAndCheckitemService checkgroupAndCheckitemService;

  @PostMapping("/add")
  public Result add(@RequestBody CheckItem checkItem){
    Result result = null;
    try {
      checkItemService.add(checkItem);
    } catch (Exception e) {
      e.printStackTrace();
      return result = new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
    }
    return result = new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
  }

  @PostMapping("/findPage")
  public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
    PageResult pageResult = checkItemService.pageQuery(queryPageBean);
    return pageResult;
  }

  @GetMapping("delete")
  public Result deleteById(@RequestParam("id") Integer id){

    Result result = null;

    //如果id为空 直接返回删除失败
    if(id == null ){
      return result = new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
    }

    try {
      //检测是否还有关联数据
      Long count = checkgroupAndCheckitemService.selectCountByCheckitemId(id);

      //有关联数据则返回失败
      if(count > 0){
        return result = new Result(false,"该检查项有关联检查组的数据，不允许删除!");
      }else{
        checkItemService.deleteById(id);
      }
    } catch (Exception e) {
      e.printStackTrace();
      return result = new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
    }
    return result = new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
  }

  @PostMapping("/edit")
  public Result edit(@RequestBody CheckItem checkItem){

    Result result = null;

    //如果传入数据为空 则不执行方法
    if(checkItem == null){

      return result = new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);

    }

    //更新
    try {
      checkItemService.edit(checkItem);
    } catch (Exception e) {
      e.printStackTrace();
      return result = new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
    }
    return result = new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
  }

  @GetMapping("/findById")
  public Result findById(Integer id){

    Result result = null;

    try {
      CheckItem checkItem = checkItemService.selectById(id);
      if(checkItem == null){
        return result = new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL);
      }
      return result = new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
    } catch (Exception e) {
      e.printStackTrace();
      return result = new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL);
    }
  }

  @GetMapping("/findAll")
  public Result findAll(){

    Result result = null;
    List<CheckItem> all = null;

    try {
      all = checkItemService.findAll();
    } catch (Exception e) {
      e.printStackTrace();
      return result = new Result(false,"查询检查项失败!");
    }

    return result = new Result(true,"查询检查项成功!",all);
  }
}
