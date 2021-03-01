package com.lck.reverse.stu.juc;

public class SingleDemo {
    private static SingleDemo singleDemo=null;
    private SingleDemo(){
        System.out.println("单例模式 signleDemo");
    }
    public static SingleDemo getInstance(){
        if(singleDemo==null){
            return singleDemo=new SingleDemo();
        }
        return singleDemo;
    }

    public static void main(String[] args) {
//        System.out.println( SingleDemo.getInstance()==SingleDemo.getInstance());
//        System.out.println( SingleDemo.getInstance()==SingleDemo.getInstance());
//        System.out.println( SingleDemo.getInstance()==SingleDemo.getInstance());

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                SingleDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
