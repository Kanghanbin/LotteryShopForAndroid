package com.jiuyuhulian.lotteryshop.model;

/**
 * Created by khb on 2017/5/10.
 */

public class getBalance {

    /**
     * code : 0
     * data : {"money":"账户余额"}
     */

    private int code;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * money : 账户余额
         */

        private String money;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }
}
