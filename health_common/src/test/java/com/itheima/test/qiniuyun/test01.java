package com.itheima.test.qiniuyun;

import com.itheima.utils.MD5Utils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName test01
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date  2020年01月29日 14:30
 * @Version 1.0.0
*/
public class test01 {

  @Test
  public void test02(){

    String str1 = "多消毒来勤洗手，杀死病毒没后患。出门就把口罩戴，切莫随意乱吐痰。发热症状及早看，自行隔离不传染。 风雨压不垮，苦难中开花。你就是我，我就是你，天耀中华！#武汉加油共渡难关#";

    char[] chars1 = str1.toCharArray();
    List list = new ArrayList();

    char[] chars2 = new char[chars1.length];

    int length = chars2.length;
    for (char aChar : chars1) {

      chars2[length - 1] = aChar;

      length --;

    }

    System.out.println(chars2);

  }
}
