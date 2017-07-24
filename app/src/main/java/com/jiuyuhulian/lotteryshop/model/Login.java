package com.jiuyuhulian.lotteryshop.model;

/**
 * Created by khb on 2017/4/21.
 */

public class Login {

    /**
     * message:
     * code : 0
     * data : {"token":"用户身份令牌","code_id":"店铺ID","staff_id":"操作员ID"}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public static class DataBean {
        /**
         * token : 用户身份令牌
         * code_id : 店铺ID
         * staff_id : 操作员ID
         */

        private String token;
        private String shop_id;
        private String staff_id;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
        }
    }
}
