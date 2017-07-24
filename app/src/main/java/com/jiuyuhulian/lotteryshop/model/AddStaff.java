package com.jiuyuhulian.lotteryshop.model;

/**
 * Created by khb on 2017/4/27.
 */

public class AddStaff {

    /**
     * code : 0
     * data : {"staff_id":"操作员ID"}
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

    public static class DataBean {
        /**
         * staff_id : 操作员ID
         */

        private String staff_id;

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
        }
    }
}
