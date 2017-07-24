package com.jiuyuhulian.lotteryshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by khb on 2017/4/24.
 */

public class AddressList {

    /**
     * code : 0
     * data : [{"id":"","shop_id":"店铺ID","username":"姓名","mobile":"手机","city":"省市区","address":"详细地址","is_default":"1: 默认;2.非默认"}]
     */

    private int code;
    private List<DataBean> data;
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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id :
         * shop_id : 店铺ID
         * username : 姓名
         * mobile : 手机
         * city : 省市区
         * address : 详细地址
         * is_default : 1: 默认;2.非默认
         */

        private String address_id;
        private String shop_id;
        private String username;
        private String mobile;
        private String city;
        private String address;
        private String is_default;

        public String getId() {
            return address_id;
        }

        public void setId(String address_id) {
            this.address_id = address_id;
        }

        public String getShop_id() {
            return shop_id;
        }

        public void setShop_id(String shop_id) {
            this.shop_id = shop_id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIs_default() {
            return is_default;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }
    }
}
