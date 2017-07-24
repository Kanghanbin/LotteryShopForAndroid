package com.jiuyuhulian.lotteryshop.model;

/**
 * Created by khb on 2017/4/22.
 */

public class SendCode {

    /**
     * code : 0
     * data : null
     */

    private int code;
    private Object data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
