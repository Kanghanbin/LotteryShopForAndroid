package com.jiuyuhulian.lotteryshop.model;

import java.io.Serializable;

/**
 * Created by khb on 2017/4/27.
 */

public class CreateOrderResponse {

    /**
     * code : 0
     * data : {"money":"账户余额"}
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * money : 账户余额
         * order_id:订单号
         *
         */

        private double money;
        private String order_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }
    }
}
