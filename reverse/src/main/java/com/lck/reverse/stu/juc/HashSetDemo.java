package com.lck.reverse.stu.juc;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

public class HashSetDemo {

    public static void main(String[] args) {
//        Set<String> set=new HashSet<>();
//        Set<String> set=new CopyOnWriteArraySet<>();
        Set<String> set= Collections.synchronizedSet(new HashSet<>());

        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(8));
                System.out.println(set);
            }).start();
        }
        set.add("");

    }
}
