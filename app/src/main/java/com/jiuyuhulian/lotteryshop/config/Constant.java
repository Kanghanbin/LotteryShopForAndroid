package com.jiuyuhulian.lotteryshop.config;

import com.jiuyuhulian.lotteryshop.R;

/**
 * Created by Admin on 2017/3/6.
 */

public class Constant {

        //测试服务器
        public static final String BASEURL="http://client.lottery.test/";
        public static final String IMG_URL="http://test-static.9yu.tv";

        //彩店客户端微信SDK
        public static final String APP_ID = "wxe590b975b64c0076";
        public static final String APP_SECRET = "97c18b6507d82fe12ca040428838033e";

        //读超时长，单位：毫秒
        public static final int READ_TIME_OUT = 7676;
        //连接时长，单位：毫秒
        public static final int CONNECT_TIME_OUT = 7676;
        public static final long  MILLISINFUTURE=60*1000;
        public static final long  COUNTDOWNINTERVAL=1000;


        public static final String SERVER_ERROR="服务器错误";
        public static final String LOADING="正在加载中";
        public static final int NO_DATA=30;

        /**
         * 引导界面本地图片
         */
        public static final int[] pics = {R.drawable.welcome_one, R.drawable.welcome_two, R.drawable.welcome_three};

        public static final String SEARCHSPNAME="search";
        public static final String HTTPURL_CRASH="cash";

        /**
         * EVENTBUS传递参数
         * */
        public static final String CONTINUE_SCAN="CONTINUE_SCAN";
        public static final String PAY_REQUEST="PAY_REQUEST";

        /**
         * 下拉刷新，上拉加载
         * */
        public static final String NORMAL_GET_DATA="NORMAL_GET_DATA";
        public static final String PULL_TO_REFRESH="PULL_TO_REFRESH";
        public static final String LOAD_MORE="LOAD_MORE";

        /**
         * 彩票激活type
         * type = "类型（scan：扫码；hand：手动）"
         * */
        public static final String SCAN_ACTIVE="scan";
        public static final String HAND_ACTIVE="hand";

}
