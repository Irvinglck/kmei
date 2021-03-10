package com.lck.reverse.entity.respon;

/**
 * lck
 */
public class ResultMessage {

    private int code = 0;

    private String msg;

    private Object data;


    public static ResultMessage getDefaultResultMessage() {
        return new ResultMessage();
    }

    public static ResultMessage getDefaultResultMessage(Object data) {
        return new ResultMessage().setData(data);
    }

    public static ResultMessage getDefaultResultMessage(int code, String msg) {
        return new ResultMessage().setCode(code).setMsg(msg);
    }

    public static ResultMessage getDefaultResultMessage(int code, Object obj) {
        return new ResultMessage().setCode(code).setData(obj);
    }

    public static ResultMessage getDefaultResultMessage(int code, String msg, Object data) {
        return new ResultMessage().setCode(code).setMsg(msg).setData(data);
    }

    public int getCode() {
        return code;
    }

    public ResultMessage setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultMessage setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResultMessage setData(Object data) {
        this.data = data;
        return this;
    }
}
