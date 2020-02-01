package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import com.itheima.utils.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @ClassName ReportController
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年01月30日 15:10
 * @Version 1.0.0
*/
@RestController()
@RequestMapping("/report")
public class ReportController {

  @Reference
  private MemberService memberService;

  @Reference
  private SetmealService setmealService;

  @Reference
  private ReportService reportService;

  /**
   * @Author YongXi.Wang
   * @Description 统计每月会员注册数量报表
   * @Date 2020/1/30 20:06
   * @Param [] 
   * @return com.itheima.entity.Result
  **/
  @GetMapping("/getMemberReport")
  public Result getMemberReport() throws Exception{

    try {
      //创建封装map
      Map<String,Object> map = new HashMap<>();
      //月份封装类
      List<String> months = new ArrayList<>();


      //格式化时间 年月
      Calendar calendar = Calendar.getInstance();
      calendar.add(Calendar.MONTH,-12);

      for(int i = 1; i <= 12; i++){
        calendar.add(Calendar.MONTH,1);
        Date time = calendar.getTime();
        //封装时间
        months.add(DateUtils.parseDate2String(time,"YYYY-MM"));
      }

      //会员数量封装类
      List<Integer> memberCount = memberService.getMemeberCountByMonth(months);

      map.put("months",months);
      map.put("memberCount",memberCount);

      return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
    }
  }
  
  /**
   * @Author YongXi.Wang
   * @Description 统计套餐占比报表
   * @Date 2020/1/30 20:06
   * @Param  
   * @return 
  **/
  @GetMapping("/getSetmealReport")
  public Result getSetmealReport(){

    try {
      //创建封装返回类
      Map<String,List> map = new HashMap();

      //查询检查套餐占比
      List<Map<String,Object>> setmealCountList = setmealService.findSetmealCount();

      List<String> setmealNameList = new ArrayList<>();

      //取出name值
      for (Map<String, Object> setmealCountMap : setmealCountList) {
        setmealNameList.add((String) setmealCountMap.get("name"));
      }

      map.put("setmealCount",setmealCountList);
      map.put("setmealNames",setmealNameList);

      return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
    }
  }

  /**
   * @Author YongXi.Wang
   * @Description 运营统计报表
   * @Date 2020/1/30 20:06
   * @Param
   * @return
   **/
  @GetMapping("/getBusinessReportData")
  public Result getBusinessReportData(){

    try {
      Map<String, Object> businessReportData = reportService.getBusinessReportData();
      return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,businessReportData);
    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
    }

  }

  /**
   * @Author YongXi.Wang
   * @Description 导出运营统计报表
   * @Date 2020/1/30 20:06
   * @Param
   * @return
   **/
  @GetMapping("/exportBusinessReport")
  public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){

    ServletOutputStream out = null;

    try {
      //远程调用报表服务获取报表数据
      Map<String, Object> result = reportService.getBusinessReportData();

      //取出返回结果数据，准备将报表数据写入到Excel文件中
      String reportDate = (String) result.get("reportDate");
      Integer todayNewMember = (Integer) result.get("todayNewMember");
      Integer totalMember = (Integer) result.get("totalMember");
      Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
      Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
      Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
      Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
      Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
      Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
      Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
      Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
      List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

      //读取项目中的模板文件
      String templateName = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";

      //获取excel对象
      XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(templateName)));

      //获取第一个标签
      XSSFSheet sheet = xssfWorkbook.getSheetAt(0);

      //获取第二行 行的下标从1开始 列从0开始
      XSSFRow row = sheet.getRow(2);
      //获取第六列
      XSSFCell cell = row.getCell(5);
      cell.setCellValue(reportDate);//报表日期

      row = sheet.getRow(4);
      row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
      row.getCell(7).setCellValue(totalMember);//总会员数

      row = sheet.getRow(5);
      row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
      row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

      row = sheet.getRow(7);
      row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
      row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

      row = sheet.getRow(8);
      row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
      row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

      row = sheet.getRow(9);
      row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
      row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

      int rowNum = 12;
      for(Map map : hotSetmeal){//热门套餐
        String name = (String) map.get("name");
        Long setmeal_count = (Long) map.get("setmeal_count");
        BigDecimal proportion = (BigDecimal) map.get("proportion");
        row = sheet.getRow(rowNum ++);
        row.getCell(4).setCellValue(name);//套餐名称
        row.getCell(5).setCellValue(setmeal_count);//预约数量
        row.getCell(6).setCellValue(proportion.doubleValue());//占比
      }

      //通过输出流进行文件下载
     out = response.getOutputStream();
      //指定格式
      response.setContentType("application/vnd.ms-excel");
      response.setHeader("content-Disposition", "attachment;filename=report.xlsx");

      xssfWorkbook.write(out);

      out.flush();

      return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS);

    } catch (Exception e) {
      e.printStackTrace();
      return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
    }finally {
      if(out != null){
        try {
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
