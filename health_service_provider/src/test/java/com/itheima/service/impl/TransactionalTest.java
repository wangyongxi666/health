package com.itheima.service.impl;

import com.itheima.dao.SetmealMapper;
import com.itheima.pojo.Setmeal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName com.itheima.service.impl.TransactionalTest
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年01月11日 22:04
 * @Version 1.0.0
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-service.xml",
        "classpath:spring-dao.xml",
        "classpath:spring-tx.xml",
        "classpath:spring-redis.xml"})
@Transactional
@Rollback(false)
public class TransactionalTest {

  @Autowired
  private SetmealMapper setmealMapper;

  @Test
  public void test01(){

    System.out.println("开始------------------------------:" + 123);

    System.out.println("插入第一个对象------------------------------");
//    Setmeal setmeal1 = new Setmeal();
//    setmeal1.setName("111");
//    setmeal1.setCode("333");
//
//    setmealMapper.insertSelective(setmeal1);
//
//    System.out.println(setmeal1.getId());
//
//    System.out.println("插入第二个对象------------------------------");
//
//    System.out.println("发生错误------------------------------");
//
//    int i = 1 / 0;
//
//    Setmeal setmeal2 = new Setmeal();
//    setmeal2.setName("666");
//    setmeal2.setCode("888");
//
//    setmealMapper.insertSelective(setmeal2);
//
//    System.out.println(setmeal2.getId());

  }
}
