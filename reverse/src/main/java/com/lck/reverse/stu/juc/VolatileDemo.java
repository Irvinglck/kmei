package com.lck.reverse.stu.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {
    volatile int data = 0;

     void addData() {
        this.data= 60;
    }

    void addPlusDaa(){
         data++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    void autoMyInter(){
        atomicInteger.getAndIncrement();
    }
}

public class VolatileDemo {
    //演示不保证原子性&解决原子性问题
    public static void main(String[] args) {
        MyData m=new MyData();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                for (int i1 = 0; i1 < 200; i1++) {
                    m.addPlusDaa();
                    m.autoMyInter();
                }
            }).start();

        }
        //或者线程数
        while(Thread.activeCount()>2){
            //让出cpu执行权
            Thread.yield();
        }
        System.out.println("最终值 "+ m.data);
        System.out.println("最终值 atomic "+ m.atomicInteger);
    }

    //演示volatie原子性
    public static void see(String[] args) {
        MyData myData=new MyData();
        System.out.println(Thread.currentThread().getName()+ "  线程更新值之前 " + myData.data);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addData();
            System.out.println(Thread.currentThread().getName()+ "  线程更新值之后 " + myData.data);

        }, "AAAA").start();



        while(myData.data==0){
//            System.out.println("主线程名称 "+ Thread.currentThread().getName());
        }
        System.out.println("主线程名称 hello "+ Thread.currentThread().getName());
    }

    //指令重拍可能的代码

    private int i=0;
    private boolean flag=false;

    public void method1(){
        this.i=1;
        this.flag=true;
    }
    //单线程没问题，
    //多线程出现指令重拍，this.flag=true先执行，然后继续执行method2()
    //会出现 i 取值为 5 的情况
    public void method2(){
        if(flag==true){
            System.out.println("i取值 "+ i+5);
        }
    }
}



