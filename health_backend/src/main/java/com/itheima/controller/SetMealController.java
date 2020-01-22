package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import com.itheima.utils.QiNiuUtil;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName SetMealController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2019年12月27日 16:19
 * @Version 1.0.0
*/
@RestController
@RequestMapping("/setmeal")
public class SetMealController {

  @Autowired
  private JedisPool jedisPool;

  @Reference
  private SetmealService setmealService;

  /**
   * 上传图片
  **/
  @RequestMapping("/upload")
  public Result upload(MultipartFile imgFile){

    //截取后缀
    String originalFilename = imgFile.getOriginalFilename();
    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

    //生成文件名
    String fileName = UUID.randomUUID().toString() + suffix;

    try {
      //上传文件
      QiNiuUtil.upload2Qiniu(imgFile.getBytes(),fileName);
      //保存文件名到redis
      jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
    } catch (IOException e) {
      e.printStackTrace();
      return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }
    return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
  }

  /**
   * 新增检查套餐
  **/
  @RequestMapping("/add")
  public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds){
    try {
      setmealService.add(setmeal,checkgroupIds);

    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
    }
    return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
  }

  @PostMapping("/findPage")
  public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
    return setmealService.findPage(queryPageBean);
  }

  @GetMapping("/delete")
  public Result delete(int id){
    try {
      setmealService.delete(id);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false, "删除失败");
    }
    return new Result(true, "删除成功");
  }

}
