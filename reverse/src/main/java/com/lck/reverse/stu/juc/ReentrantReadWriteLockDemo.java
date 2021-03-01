package com.lck.reverse.stu.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDemo {

    private volatile Map<String, String> map = new HashMap<>();

    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private void addData(String key, String value) {
        try {
            //独占锁
            readWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + "\t正在写入：" + key);
            map.put(key, value);
            TimeUnit.MICROSECONDS.sleep(500);
            System.out.println(Thread.currentThread().getName() + "\t写入完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    private void findData(String key) {
        try {
            //共享锁
            readWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "\t正在读取：" + key);
            String result = map.get(key);
            TimeUnit.MICROSECONDS.sleep(500);
            System.out.println(Thread.currentThread().getName() + "\t读取完成: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            readWriteLock.readLock().unlock();
        }

    }

    public static void main(String[] args) {
        ReentrantReadWriteLockDemo reentrantReadWriteLockDemo = new ReentrantReadWriteLockDemo();
        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                reentrantReadWriteLockDemo.addData(String.valueOf(temp), String.valueOf(temp));
            }, "Thread " + i).start();
        }

        for (int i = 0; i < 5; i++) {
            final int temp = i;
            new Thread(() -> {
                reentrantReadWriteLockDemo.findData(String.valueOf(temp));
            }, "Thread " + i).start();
        }
    }
}
