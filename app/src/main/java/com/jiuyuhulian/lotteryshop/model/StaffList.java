package com.jiuyuhulian.lotteryshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by khb on 2017/4/27.
 */

public class StaffList {


    /**
     * code : 0
     * data : [{"staff_id":"操作员ID","staff":"编号"},{"staff_id":"操作员ID","staff":"编号"},"......"]
     */

    private int code;
    private List<DataBean> data;
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean implements Serializable{
        /**
         * staff_id : 操作员ID
         * staff : 编号
         * mobile :手机
         */

        private String staff_id;
        private String staff;
        private String mobile;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getStaff_id() {
            return staff_id;
        }

        public void setStaff_id(String staff_id) {
            this.staff_id = staff_id;
        }

        public String getStaff() {
            return staff;
        }

        public void setStaff(String staff) {
            this.staff = staff;
        }
    }
}
