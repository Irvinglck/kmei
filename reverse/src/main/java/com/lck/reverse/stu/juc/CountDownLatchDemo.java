package com.lck.reverse.stu.juc;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t 离开教室");
                countDownLatch.countDown();
            },CountEnumDemo.getCountEnumByCode(i).getMsg()).start();
        }
        countDownLatch.await();
        System.out.println("秦 离开教室");

    }
}
