package com.jiuyuhulian.lotteryshop.model;

/**
 * Created by khb on 2017/4/27.
 */

public class AddBankCard {

    /**
     * code : 0
     * data : {"bank_id":"银行卡ID"}
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
         * bank_id : 银行卡ID
         */

        private String bank_id;

        public String getBank_id() {
            return bank_id;
        }

        public void setBank_id(String bank_id) {
            this.bank_id = bank_id;
        }
    }
}
