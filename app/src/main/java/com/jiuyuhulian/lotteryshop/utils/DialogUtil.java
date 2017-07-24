package com.jiuyuhulian.lotteryshop.utils;

import android.content.Context;

import com.jiuyuhulian.lotteryshop.config.Constant;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by khb on 2017/4/22.
 */

public class DialogUtil {

    public static Context mcontext;
    public static SweetAlertDialog mDialog;

    public DialogUtil(Context mcontext) {
        this.mcontext = mcontext;
    }

    public static void show(){
        dismiss();
        mDialog=new SweetAlertDialog(mcontext,SweetAlertDialog.PROGRESS_TYPE)
                 .setTitleText(Constant.LOADING);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

    }
    public static void dismiss(){
        if(mDialog!=null&&mDialog.isShowing()){
            mDialog.dismissWithAnimation();
        }
        mDialog=null;
    }




}
