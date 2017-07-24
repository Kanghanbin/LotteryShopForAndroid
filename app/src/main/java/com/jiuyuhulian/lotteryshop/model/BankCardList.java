package com.jiuyuhulian.lotteryshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by khb on 2017/4/27.
 */

public class BankCardList {


    /**
     * code : 0
     * data : [{"bank_id":"银行卡ID","card_number":"银行卡号","bank":"银行","card_type":"卡类型"}]
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
         * bank_id : 银行卡ID
         * card_number : 银行卡号
         * bank : 银行
         * card_type : 卡类型
         */

        private String bank_id;
        private String card_number;
        private String bank;
        private String card_type;

        public String getBank_id() {
            return bank_id;
        }

        public void setBank_id(String bank_id) {
            this.bank_id = bank_id;
        }

        public String getCard_number() {
            return card_number;
        }

        public void setCard_number(String card_number) {
            this.card_number = card_number;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        public String getCard_type() {
            return card_type;
        }

        public void setCard_type(String card_type) {
            this.card_type = card_type;
        }
    }
}
