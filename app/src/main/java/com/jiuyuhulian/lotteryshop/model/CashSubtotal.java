package com.jiuyuhulian.lotteryshop.model;

import java.util.List;

/**
 * Created by khb on 2017/5/11.
 */

public class CashSubtotal {

    /**
     * code : 0
     * data : {"lottery":[{"encash_time":"兑奖时间","win_money":"中奖金额","cash_id":"纪录ID","lottery":"彩票名称","img":"图片地址"},"......"],"sum":"数量","money":"金额"}
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
         * lottery : [{"encash_time":"兑奖时间","win_money":"中奖金额","cash_id":"纪录ID","lottery":"彩票名称","img":"图片地址"},"......"]
         * sum : 数量
         * money : 金额
         */

        private String sum;
        private double money;
        private List<LotteryBean> lottery;

        public String getSum() {
            return sum;
        }

        public void setSum(String sum) {
            this.sum = sum;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }

        public List<LotteryBean> getLottery() {
            return lottery;
        }

        public void setLottery(List<LotteryBean> lottery) {
            this.lottery = lottery;
        }

        public static class LotteryBean {
            /**
             * encash_time : 兑奖时间
             * win_money : 中奖金额
             * cash_id : 纪录ID
             * lottery : 彩票名称
             * img : 图片地址
             */

            private String encash_time;
            private String win_money;
            private String cash_id;
            private String lottery;
            private String img;
            private double price;

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getEncash_time() {
                return encash_time;
            }

            public void setEncash_time(String encash_time) {
                this.encash_time = encash_time;
            }

            public String getWin_money() {
                return win_money;
            }

            public void setWin_money(String win_money) {
                this.win_money = win_money;
            }

            public String getCash_id() {
                return cash_id;
            }

            public void setCash_id(String cash_id) {
                this.cash_id = cash_id;
            }

            public String getLottery() {
                return lottery;
            }

            public void setLottery(String lottery) {
                this.lottery = lottery;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
