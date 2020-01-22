package com.itheima.test.qiniuyun;

/**
 * @ClassName WaitAndNotify
 * @Description (这里用一句话描述这个类的作用)
 * @Author YongXi.Wang
 * @Date 2020年01月17日 9:21
 * @Version 1.0.0
 */

class Data {
  public String sex = "女";
  public String name = "小红";
  public boolean flag = false;
}

//消费者
class Read extends Thread {

  private Data data;

  public Read(Data data) {
    this.data = data;
  }

  @Override
  public void run() {
    while (true) {
      synchronized (data) {
        if(!data.flag){
          try {
            data.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        System.out.println(data.name + " , " + data.sex);
        data.flag = false;
        data.notify();
      }

    }
  }
}

//生成者
class Write extends Thread {

  private Data data;

  public Write(Data data) {
    this.data = data;
  }

  @Override
  public void run() {

    int count = 0;

    while (true) {
      synchronized (data) {
        if(data.flag){
          try {
            data.wait();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
        if (count == 0) {
          data.name = "小红";
          data.sex = "女";
        } else {
          data.name = "小王";
          data.sex = "男";
        }
        data.flag = true;
        data.notify();
      }

      count = (count + 1) % 2;
    }

  }
}

public class WaitAndNotify {

  public static void main(String[] args) {
    Data data = new Data();

    Write write = new Write(data);
    Read read = new Read(data);

    write.start();
    read.start();
  }

}
