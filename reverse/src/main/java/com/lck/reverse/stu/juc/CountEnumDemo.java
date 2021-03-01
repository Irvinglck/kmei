package com.lck.reverse.stu.juc;

import lombok.Getter;


public enum  CountEnumDemo {
    ONE(1,"燕"),TWO(2,"韩"),THREE(3,"赵")
    ,FOUR(4,"魏"),FIVE(5,"齐"),SIX(6,"楚");

    CountEnumDemo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    @Getter
    private Integer code;

    @Getter
    private String msg;

    public static CountEnumDemo getCountEnumByCode(int code){
        CountEnumDemo[] values = CountEnumDemo.values();
        for(CountEnumDemo value: values){
            if(code==value.getCode()){
                return value;
            }
        }
        return null;
    }
}
