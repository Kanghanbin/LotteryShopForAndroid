package com.jiuyuhulian.lotteryshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khb on 2017/5/3.
 */

public class WeChatPrepay {

    /**
     * code : 0
     * data : {"orderId":"充值订单ID","appId":"应用ID","partnerId":"商户号","prepayId":"预下单订单号","nonceStr":"随机字符串","package":"Sign=WXPay","timestamp":"时间戳","sign":"签名"}
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
         * orderId : 充值订单ID
         * appId : 应用ID
         * partnerId : 商户号
         * prepayId : 预下单订单号
         * nonceStr : 随机字符串
         * package : Sign=WXPay
         * timestamp : 时间戳
         * sign : 签名
         */

        private String orderId;
        private String appId;
        private String partnerId;
        private String prepayId;
        private String nonceStr;
        @SerializedName("package")
        private String packageX;
        private String timestamp;
        private String sign;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
