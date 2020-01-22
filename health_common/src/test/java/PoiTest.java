import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName PoiTest
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2019年12月29日 10:41
 * @Version 1.0.0
*/
public class PoiTest {

  //读入
//  @Test
  public void test01(){

    XSSFWorkbook excel = null;

    try {
      //获取excel对象
      excel = new XSSFWorkbook(new FileInputStream(new File("E:\\work\\study\\项目\\健康医疗\\poitest.xlsx")));
      //获取excel的第一个标签页
      XSSFSheet sheetAt = excel.getSheetAt(0);
      //遍历行
      for (Row row : sheetAt) {
        //遍历列
        for (Cell cell : row) {
          System.out.print(cell.getStringCellValue() + "\t");
        }
        System.out.println();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if(excel != null){
        try {
          excel.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  //写出
//  @Test
  public void test02() throws Exception{

    //创建excel对象
    XSSFWorkbook excel = new XSSFWorkbook();
    //创建标签页
    XSSFSheet sheet = excel.createSheet("传智播客");

    //创建第一行
    XSSFRow rowTital = sheet.createRow(0);
    //创建第一列
    rowTital.createCell(0).setCellValue("姓名");
    rowTital.createCell(1).setCellValue("城市");

    //创建第二行
    XSSFRow row2 = sheet.createRow(1);
    //创建第二列
    row2.createCell(0).setCellValue("张三");
    row2.createCell(1).setCellValue("南京");

    //创建输出流
    FileOutputStream out = new FileOutputStream("E:\\work\\study\\项目\\健康医疗\\poi写出测试.xlsx");

    //写出
    excel.write(out);
    out.flush();

    //关闭资源
    out.close();
    excel.close();
  }

}
