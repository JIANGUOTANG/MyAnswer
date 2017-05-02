package com.example.jian.myanswer.presenter;

import android.widget.LinearLayout;

/**
 * Created by jian on 2017/2/4.
 */

public interface PractivePresenter {
    void loadPractive();//加载练习
     void showPractice();
     void onClickA(LinearLayout v);//点击A
     void onClickB(LinearLayout v);//点击B
     void onClickC(LinearLayout v);//点击C
     void onClickD(LinearLayout v);//点击D
}
