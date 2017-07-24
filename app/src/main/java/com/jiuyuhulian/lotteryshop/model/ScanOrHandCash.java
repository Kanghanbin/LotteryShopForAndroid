package com.jiuyuhulian.lotteryshop.model;

/**
 * Created by khb on 2017/4/28.
 */

public class ScanOrHandCash {

    /**
     * code : 0
     * data : {"username":"Gary","codeStatus":1,"scanDesc":"","encashDesc":"彩票中得1等奖，奖金100万","agreementUrl":"www.qq.com","activityNo":"abcdefg123"}
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
         * username : Gary
         * codeStatus : 1
         * scanDesc :
         * encashDesc : 彩票中得1等奖，奖金100万
         * agreementUrl : www.qq.com
         * activityNo : abcdefg123
         */

        private String username;
        private int codeStatus;
        private String scanDesc;
        private String encashDesc;
        private String agreementUrl;
        private String activityNo;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getCodeStatus() {
            return codeStatus;
        }

        public void setCodeStatus(int codeStatus) {
            this.codeStatus = codeStatus;
        }

        public String getScanDesc() {
            return scanDesc;
        }

        public void setScanDesc(String scanDesc) {
            this.scanDesc = scanDesc;
        }

        public String getEncashDesc() {
            return encashDesc;
        }

        public void setEncashDesc(String encashDesc) {
            this.encashDesc = encashDesc;
        }

        public String getAgreementUrl() {
            return agreementUrl;
        }

        public void setAgreementUrl(String agreementUrl) {
            this.agreementUrl = agreementUrl;
        }

        public String getActivityNo() {
            return activityNo;
        }

        public void setActivityNo(String activityNo) {
            this.activityNo = activityNo;
        }
    }
}
