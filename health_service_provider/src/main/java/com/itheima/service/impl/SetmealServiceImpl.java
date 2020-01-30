package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import freemarker.template.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SetmealServiceImpl
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2019年12月27日 19:41
 * @Version 1.0.0
*/
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

  @Autowired
  private SetmealMapper setmealMapper;

  @Autowired
  private JedisPool jedisPool;

  @Autowired
  private FreeMarkerConfigurer freeMarkerConfig;

  @Value("${out_put_path}")
  private String freeMarkerOutPutPath;

  @Override
  public void add(Setmeal setmeal, Integer[] checkGroupIds) {

    //插入数据
    setmealMapper.insertSelective(setmeal);

    Integer setmealId = setmeal.getId();

    addSetmealandCheckgroup(setmealId,checkGroupIds);

    String imgName = setmeal.getImg();

    if(imgName != null && !imgName.isEmpty()){
      //把实际关联的图片名存入db redis中
      jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,imgName);
    }

    //添加套餐后 生成静态页面
    generateMobileStaticHtml();
  }

  /**
   * 生成静态页面
  **/
  public void generateMobileStaticHtml(){
    //查询数据
    List<Setmeal> list = setmealMapper.findAll();

    //生成套餐列表模板
    generateMobileSetmealListHtml(list);

    //生成套餐详情模板
    generateMobileSetmealDetailHtml(list);
  }

  /**
   * 生成套餐列表模板
   **/
  public void generateMobileSetmealListHtml(List<Setmeal> list){
    Map map = new HashMap();
    map.put("setmealList",list);

    generateHtml("mobile_setmeal.ftl","m_setmeal.html",map);
  }

  /**
   * 生成套餐详情模板
   **/
  public void generateMobileSetmealDetailHtml(List<Setmeal> list){
    for (Setmeal setmeal : list) {

      Map map = new HashMap();

      map.put("setmeal",setmealMapper.findById(setmeal.getId()));

      generateHtml
              ("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",map);
    }
  }
  

  /**
   * 输出静态页面
  **/
  public void generateHtml(String templateName,String htmlPageName,Map map) {

    Configuration configuration = freeMarkerConfig.getConfiguration();

    FileWriter writer = null;

    try {
      Template template = configuration.getTemplate(templateName);

      writer = new FileWriter(new File(freeMarkerOutPutPath + "/" + htmlPageName));

      template.process(map,writer);

    } catch (Exception e) {
      e.printStackTrace();
    }finally {
      if(writer != null){
        try {
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public PageResult findPage(QueryPageBean queryPageBean) {

    Integer currentPage = queryPageBean.getCurrentPage();
    Integer pageSize = queryPageBean.getPageSize();
    String queryString = queryPageBean.getQueryString();

    PageHelper.startPage(currentPage,pageSize);

    Page<Setmeal> page = setmealMapper.findPage(queryString);

    PageResult pageResult = new PageResult(page.getTotal(),page.getResult());

    return pageResult;
  }

  private void addSetmealandCheckgroup(Integer setmealId, Integer[] checkGroupIds) {

    if(checkGroupIds != null && checkGroupIds.length > 0){
      //清楚所有关联
      setmealMapper.delAndCheckGroup(setmealId);
      //重新建立关联
      for (Integer checkGroupId : checkGroupIds) {
        Map map = new HashMap<>();
        map.put("setmealId",setmealId);
        map.put("checkGroupId",checkGroupId);
        setmealMapper.addSetmealandCheckgroup(map);
      }
    }

  }

  @Override
  public void delete(int id) {
    //删除关联关系
    setmealMapper.delAndCheckGroup(id);

    //删除检查组
    setmealMapper.deleteById(id);
  }

  @Override
  public List<Setmeal> findAll() {
    return setmealMapper.select();
  }

  @Override
  public Setmeal findById(int id) {
    return setmealMapper.findById(id);
  }

  @Override
  public List<Map<String, Object>> findSetmealCount() {
    return setmealMapper.findSetmealCount();
  }

}
