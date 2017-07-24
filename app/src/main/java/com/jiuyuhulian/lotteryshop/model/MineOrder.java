package com.jiuyuhulian.lotteryshop.model;

import java.util.List;

/**
 * Created by khb on 2017/5/8.
 */

public class MineOrder {

    /**
     * code : 0
     * data : [{"id":"订单ID","freight":"运费","sum_money":"总价","pay_type":"支付方式，1：支付宝；2：微信；3：银行卡；4：余额","status":"订单状态 ，1：待付款；2：待发货；5：已发货；6：已签收；7：订单成功；8：订单失败","pay_status":"支付状态，1：支付成功；2：未支付；3：支付失败","create_time":"下单时间","pay_time":"付款时间","send_time":"发货时间","accept_time":"收货时间","finish_time":"订单完成时间","order_lottery":[{"order_lottery_id":"订单详情ID","order_id":"订单ID","lottery_id":"彩票ID","quantity":"数量","money":"总价","add_time":"添加时间","lottery":"彩票名称","img":"图片地址"}]}]
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
         * order_id  : 订单ID
         * freight : 运费
         * sum_money : 总价
         * pay_type : 支付方式，1：支付宝；2：微信；3：银行卡；4：余额
         * status : 订单状态 ，1：待付款；2：待发货；5：已发货；6：已签收；7：订单成功；8：订单失败
         * pay_status : 支付状态，1：支付成功；2：未支付；3：支付失败
         * create_time : 下单时间
         * pay_time : 付款时间
         * send_time : 发货时间
         * accept_time : 收货时间
         * finish_time : 订单完成时间
         * order_number : 订单号
         * order_lottery : [{"order_lottery_id":"订单详情ID","order_id":"订单ID","lottery_id":"彩票ID","quantity":"数量","money":"总价","add_time":"添加时间","lottery":"彩票名称","img":"图片地址"}]
         */

        private String order_id;
        private String freight;
        private String sum_money;
        private int pay_type;
        private int status;
        private String pay_status;
        private String create_time;
        private String pay_time;
        private String send_time;
        private String accept_time;
        private String finish_time;
        private String order_number;
        private List<OrderLotteryBean> order_lottery;

        public String getId() {
            return order_id;
        }

        public void setId(String order_id) {
            this.order_id = order_id;
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

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public String getAccept_time() {
            return accept_time;
        }

        public void setAccept_time(String accept_time) {
            this.accept_time = accept_time;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }


        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_number() {
            return order_number;
        }

        public void setOrder_number(String order_number) {
            this.order_number = order_number;
        }

        public List<OrderLotteryBean> getOrder_lottery() {
            return order_lottery;
        }

        public void setOrder_lottery(List<OrderLotteryBean> order_lottery) {
            this.order_lottery = order_lottery;
        }

        public static class OrderLotteryBean {
            /**
             * order_lottery_id : 订单详情ID
             * order_id : 订单ID
             * lottery_id : 彩票ID
             * quantity : 数量
             * money : 总价
             * add_time : 添加时间
             * lottery : 彩票名称
             * img : 图片地址
             */

            private String order_lottery_id;
            private String order_id;
            private String lottery_id;
            private String quantity;
            private String money;
            private String add_time;
            private String lottery;
            private String img;

            public String getOrder_lottery_id() {
                return order_lottery_id;
            }

            public void setOrder_lottery_id(String order_lottery_id) {
                this.order_lottery_id = order_lottery_id;
            }

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

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

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
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
