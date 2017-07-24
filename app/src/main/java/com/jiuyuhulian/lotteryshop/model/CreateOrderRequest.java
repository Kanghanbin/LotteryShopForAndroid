package com.jiuyuhulian.lotteryshop.model;

import java.util.List;

/**
 * Created by khb on 2017/4/26.
 */

public class CreateOrderRequest {




    /**
     * shop_id : 店铺ID
     * staff_id : 操作员ID
     * address_id : 收货地址ID
     * lottery : [{"lottery_id":"商品ID","quantity":"数量","money":"本价"},{"lottery_id":"商品ID","quantity":"数量","price":"单价"}]
     * freight : 运费
     * sum_money : 总金额
     */

    private String shop_id;
    private String staff_id;
    private String address_id;
    private String freight;
    private String sum_money;
    private List<LotteryBean> lottery;




    public CreateOrderRequest(String shop_id, List<LotteryBean> lottery, String sum_money, String freight, String address_id, String staff_id) {
        this.shop_id = shop_id;
        this.lottery = lottery;
        this.sum_money = sum_money;
        this.freight = freight;
        this.address_id = address_id;
        this.staff_id = staff_id;
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

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getSum_money() {
        return sum_money;
    }

    public void setSum_money(String sum_money) {
        this.sum_money = sum_money;
    }

    public List<LotteryBean> getLottery() {
        return lottery;
    }

    public void setLottery(List<LotteryBean> lottery) {
        this.lottery = lottery;
    }

    public static class LotteryBean {
        /**
         * lottery_id : 商品ID
         * quantity : 数量
         * price : 单价
         */

        private String lottery_id;
        private String quantity;
        private String money;

        public String getLottery_id() {
            return lottery_id;
        }

        public void setLottery_id(String lottery_id) {
            this.lottery_id = lottery_id;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getMeoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public LotteryBean(String lottery_id, String money, String quantity) {
            this.lottery_id = lottery_id;
            this.money = money;
            this.quantity = quantity;
        }
    }
}
