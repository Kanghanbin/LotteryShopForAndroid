package com.jiuyuhulian.lotteryshop.model;

import java.util.List;

/**
 * Created by khb on 2017/4/25.
 */

public class QueryStock {

    /**
     * code : 0
     * data : [{"activate_status":"1","num":2,"img":"/upload/lottery/26057-1493033394.png","lottery":"中国红2"},"......"]
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

    public static class DataBean {
        /**
         * activate_status : 1
         * num : 2
         * img : /upload/lottery/26057-1493033394.png
         * lottery : 中国红2
         */

        private String activate_status;
        private int num;
        private String img;
        private String lottery;

        public String getActivate_status() {
            return activate_status;
        }

        public void setActivate_status(String activate_status) {
            this.activate_status = activate_status;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getLottery() {
            return lottery;
        }

        public void setLottery(String lottery) {
            this.lottery = lottery;
        }
    }
}
