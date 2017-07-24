package com.jiuyuhulian.lotteryshop;

/**
 * Created by Admin on 2017/3/29.
 */

public class EnterTicketEvent {
    private int msg;
    private int []startPos;

    public int[] getStartPos() {
        return startPos;
    }

    public EnterTicketEvent(int msg, int[] startPos) {
        this.msg = msg;
        this.startPos = startPos;
    }

    public void setStartPos(int[] startPos) {
        this.startPos = startPos;

    }

    public int getMsg() {
        return msg;

    }

    public void setMsg(int msg) {
        this.msg = msg;
    }


}
