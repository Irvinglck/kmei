package com.lck.reverse.entity;

import java.util.HashMap;
import java.util.Map;

public class HashDemo {
    public static void main(String[] args) {
        Map<String,Object> map=new HashMap();
        map.put("1","lck");

//        int h;
//        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        System.out.println("lck".hashCode());
        System.out.println(106964 >>> 16);

    }
}
