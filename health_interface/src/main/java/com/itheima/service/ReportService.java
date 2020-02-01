package com.itheima.service;

import java.util.Map;

/**
 * @Author YongXi.Wang
 * @Description 报表接口
 * @Date 2020/1/31 12:19
 * @Param  
 * @return 
**/
public interface ReportService {

  Map<String,Object> getBusinessReportData() throws Exception;

}
