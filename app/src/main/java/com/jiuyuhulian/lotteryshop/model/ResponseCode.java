package com.jiuyuhulian.lotteryshop.model;

/**
 * Created by khb on 2017/4/22.
 */

public class ResponseCode {

    /**
     * code : 0
     */

    private int code;
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
}
