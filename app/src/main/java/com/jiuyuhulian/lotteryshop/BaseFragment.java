package com.jiuyuhulian.lotteryshop;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Admin on 2017/3/7.
 */

public class BaseFragment  extends Fragment{
    public static final String TAG = "BaseAppFragment";

    /**
     * create fragment instance
     *
     * @param fragmentClazz
     * @param args
     * @param <T>
     * @return
     */
    public static <T extends BaseFragment> T newInstance(Class<T> fragmentClazz, Bundle args) {
        T fragment = null;
        try {
            fragment = fragmentClazz.newInstance();
            fragment.setArguments(args);
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }

}
