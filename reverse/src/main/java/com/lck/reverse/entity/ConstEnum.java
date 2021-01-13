package com.lck.reverse.entity;


public enum ConstEnum {

    SUCCESS("成功",200);

    private String msg;
    private Integer code;

    ConstEnum(String msg, Integer code){
        this.code=code;
        this.msg=msg;
    }

    public String getMsg(){
        return this.msg;
    }
}
