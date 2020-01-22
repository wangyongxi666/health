package com.itheima.test.qiniuyun;

import org.junit.Test;

/**
 * @ClassName ThreadTest
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date 2020年01月16日 14:56
 * @Version 1.0.0
 */

class Res implements Runnable {
  private volatile Integer count = 100;

  @Override
  public void run() {
    while (count > 0) {
//      synchronized (this) {
        while (count > 0) {
          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println(Thread.currentThread().getName() + "抢到了第 : " + count + "张票");
          count--;
        }
//      }

    }


  }
}

public class ThreadTest {

  public static void main(String[] args) {
    Res res = new Res();
    Res res2 = new Res();

    Thread t1 = new Thread(res,"窗口一");
    Thread t2 = new Thread(res,"窗口二");

    t1.start();
    t2.start();
  }


}
