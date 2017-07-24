package com.jiuyuhulian.lotteryshop.entity;

import java.util.List;

/**
 * Created by Admin on 2017/3/21.
 */

public class GetBankList{


    /**
     * code : 0
     * cardSupportType : 0
     * data : [{"code":"PSBC","bank":"邮储银行"},{"code":"ICBC","bank":"工商银行"},{"code":"ABC","bank":"农业银行"},{"code":"COMM","bank":"交通银行"},{"code":"BOC","bank":"中国银行"},{"code":"CCB","bank":"建设银行"},{"code":"CITIC","bank":"中信银行"},{"code":"CEB","bank":"光大银行"},{"code":"HXBANK","bank":"华夏银行"},{"code":"CMBC ","bank":"民生银行"},{"code":"GDB","bank":"广发银行"},{"code":"CMB","bank":"招商银行"},{"code":"CIB","bank":"兴业银行"},{"code":"SPDB","bank":"浦发银行"},{"code":"SPABANK","bank":"平安银行"},{"code":"BJBANK","bank":"北京银行"},{"code":"SHBANK","bank":"上海银行"},{"code":"HEBBANK","bank":"河北银行"},{"code":"NXBANK","bank":"宁夏银行"},{"code":"LCCB","bank":"廊坊银行"},{"code":"PBC","bank":"人民银行"},{"code":"RZB","bank":"日照银行"},{"code":"NCB","bank":"南昌银行"},{"code":"LZYH","bank":"兰州银行"},{"code":"JLBANK","bank":"吉林银行"},{"code":"JSBANK","bank":"晋商银行"},{"code":"JXBANK","bank":"嘉兴银行"},{"code":"HSBANK","bank":"徽商银行"},{"code":"HKB","bank":"汉口银行"},{"code":"GZB","bank":"广州银行"},{"code":"CQBANK","bank":"重庆银行"},{"code":"DBS","bank":"星展银行"},{"code":"DLB","bank":"大连银行"},{"code":"BQD","bank":"青岛银行"},{"code":"BANKWF","bank":"潍坊银行"},{"code":"CZBANK","bank":"沧州银行"},{"code":"HKBEA","bank":"东亚银行"},{"code":"NBBANK","bank":"宁波银行"},{"code":"HZCB","bank":"杭州银行"},{"code":"NJCB","bank":"南京银行"},{"code":"ZSB","bank":"浙商银行"},{"code":"WZCB","bank":"温州银行"}]
     */

    private int code;
    private int cardSupportType;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCardSupportType() {
        return cardSupportType;
    }

    public void setCardSupportType(int cardSupportType) {
        this.cardSupportType = cardSupportType;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : PSBC
         * bank : 邮储银行
         */

        private String code;
        private String bank;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }
    }
}
