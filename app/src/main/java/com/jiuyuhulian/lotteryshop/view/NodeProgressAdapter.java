package com.jiuyuhulian.lotteryshop.view;

import java.util.List;

/**
 * Created by khb on 2017/4/19.
 */

public interface NodeProgressAdapter {
    /**
     * 返回集合大小
     *
     * @return
     */
    int getCount();

    /**
     * 适配数据的集合
     *
     * @return
     */
    List<LogisticsData> getData();
}
