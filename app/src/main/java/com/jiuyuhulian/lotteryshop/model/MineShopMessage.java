package com.jiuyuhulian.lotteryshop.model;

import java.util.List;

/**
 * Created by khb on 2017/4/24.
 */

public class MineShopMessage {

    /**
     * code : 0
     * data : {"staff":"操作员","code":"门店编号","username":"店主姓名","id_card":"店主身份证","money":"可用余额","shop":[]}
     */

    private int code;
    private DataBean data;

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
         * staff : 操作员
         * code : 门店编号
         * username : 店主姓名
         * id_card : 店主身份证
         * money : 可用余额
         * shop : []
         */

        private String staff;
        private String code;
        private String username;
        private String id_card;
        private String money;
        private List<?> shop;

        public String getStaff() {
            return staff;
        }

        public void setStaff(String staff) {
            this.staff = staff;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public List<?> getShop() {
            return shop;
        }

        public void setShop(List<?> shop) {
            this.shop = shop;
        }
    }
}
