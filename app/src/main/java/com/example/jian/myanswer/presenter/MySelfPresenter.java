package com.example.jian.myanswer.presenter;

import android.content.Context;

import com.example.jian.myanswer.view.MySelfView;

/**
 * Created by jian on 2017/2/26.
 */

public class MySelfPresenter {
    private MySelfView mySelfView;
    private Context context;

    public MySelfPresenter(MySelfView mySelfView, Context context) {
        this.mySelfView = mySelfView;
        this.context = context;
    }

    public void toCollectActivity(){
        mySelfView.toCollectActivity();
    }
}
