package com.lck.reverse.stu.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

//手写实现自旋锁
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference=new AtomicReference<>();
    //自旋加锁
    public void lckLock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName()+ " 线程获取锁");
        while(!atomicReference.compareAndSet(null,thread)){

        }
    }
    //自旋解锁
    public void lckUnlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName()+ " 线程解锁");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()  +" 线程来了");
            spinLockDemo.lckLock();

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.lckUnlock();
        },"T1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        new Thread(()->{
            spinLockDemo.lckLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() +" 线程来了");
            spinLockDemo.lckUnlock();

        },"T2").start();
    }
}
