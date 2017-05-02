package com.example.jian.myanswer.view;

import android.support.v7.widget.CardView;

/**
 * Created by jian on 2017/2/2.
 */

public interface MainView {
    void showLoading();
    void toPracticeActivity(CardView cardView,int x,int y);
    void toTestActivity();
    void hideLoading();
    void showFaileError();
    void toErrorQuestionActivity(int position);
    void toCollectAvtivity();
    void  toWpsActivity();
    void toPsActivity();
    void toMyTestLibraryActivity();
    void toImportActivity();
}
