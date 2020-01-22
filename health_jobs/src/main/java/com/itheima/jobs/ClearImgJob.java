package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiNiuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @ClassName ClearImgJob
 * @Description 自定义job 定时清理垃圾图片
 * @Author YongXi.Wang
 * @Date  2019年12月28日 23:31
 * @Version 1.0.0
*/
public class ClearImgJob {

  @Autowired
  private JedisPool jedisPool;

  public void clearImg(){

    System.out.println("--------------------清除一次------------------");

    //获取垃圾图片的集合
    Set<String> set = jedisPool.getResource()
            .sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);

    if(set != null){
      for (String name : set) {
        //删除上传的垃圾图片
        QiNiuUtil.deleteFileFromQiniu(name);
        //删除redis中的垃圾图片名称
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,name);
      }
    }
  }
}
