package com.lck.reverse.stu.juc;


//多线程操作资源类
class Person {
    synchronized void sendSMS() {
        System.out.println(Thread.currentThread().getName() + "\t invoke sendSMS");
        sendEmail();
    }

    synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "\t #############invoke sendEmail");
    }
}

public class ReentrantLockDemo {

    public static void main(String[] args) {
        Person p = new Person();
        new Thread(() -> {
            p.sendSMS();
        }).start();

        new Thread(() -> {
            p.sendSMS();
        }).start();
    }
}
