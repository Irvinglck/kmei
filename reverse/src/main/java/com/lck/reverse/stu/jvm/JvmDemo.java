package com.lck.reverse.stu.jvm;

import com.lck.reverse.commons.Person;

public class JvmDemo {

    public static void main(String[] args) {
        Person person = new Person();
        //获取person的实例模板
        Class<? extends Person> aClass = person.getClass();
        //通过模板可以获取类加载器
        ClassLoader classLoader = aClass.getClassLoader();
        //appClassLoader加载器加载的类
        System.out.println(System.getProperty("java.class.path"));
        System.out.println(classLoader.getParent().toString());

//        Thread thread = new Thread();
//        thread.start();
        //返回虚拟机视图使用最大内存
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("MAX_MEMORY = " + maxMemory + "（字节）、" + (maxMemory / (double) 1024 / 1024));
        // 返回 Java 虚拟机中的内存总量
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("TOTAL_MEMORY = " + totalMemory + "（字节）、" + (totalMemory / (double) 1024 / 1024) + "MB");
    }
}
