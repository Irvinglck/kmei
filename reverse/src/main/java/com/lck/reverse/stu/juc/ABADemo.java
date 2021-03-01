package com.lck.reverse.stu.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            System.out.println(atomicReference.compareAndSet(101, 100) + "******** \t 线程" + Thread.currentThread().getName());
        }, "T1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2020) + "******** \t 线程" + Thread.currentThread().getName());
        }, "T2").start();
        System.out.println("=========加版本号的原子引用============");


        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "\t 版本号：" + stamp);

            boolean b1 = atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("第一次修改结果：" + b1);
            System.out.println(Thread.currentThread().getName() + "\t 修改一次版本号:" + atomicStampedReference.getStamp());

            boolean b = atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("第二次修改结果：" + b);
            System.out.println(Thread.currentThread().getName() + "\t 修改俩次版本号:" + atomicStampedReference.getStamp());
        }, "T3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicStampedReference.compareAndSet(100, 2020, stamp, stamp + 1) + "******** \t 线程" + Thread.currentThread().getName());
        }, "T4").start();

    }
}
