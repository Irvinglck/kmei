package com.lck.reverse.entity;

public enum EnumFilePath {
    PRODUCT("pro","PRODUCT/print/"),
    BANNER("banner","banner/"),
    NEWS("news","news/title/");
    private String msg;
    private String value;
    private EnumFilePath(String msg,String value){
        this.msg=msg;
        this.value=value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
