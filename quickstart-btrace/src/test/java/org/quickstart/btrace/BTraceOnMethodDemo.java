package org.quickstart.btrace;

import java.util.concurrent.TimeUnit;

/**
 * @author youngzil@163.com
 * @description TODO
 * @createTime 2019/11/28 18:08
 */
public class BTraceOnMethodDemo {

  public static void main(String[] args) {

    try {
      TimeUnit.SECONDS.sleep(15);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    System.out.println("start main method...");

    new Thread(() -> {
      while (true) {
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();

  }

}
