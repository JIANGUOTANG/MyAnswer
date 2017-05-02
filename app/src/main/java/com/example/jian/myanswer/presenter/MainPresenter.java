package com.example.jian.myanswer.presenter;

import com.example.jian.myanswer.view.MainView;

/**
 * Created by jian on 2017/2/2.
 */

public class MainPresenter {
    private MainView mainView;
    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }
    public void loadingPractice(){
        mainView.showLoading();

      //  mainView.toPracticeActivity();
        mainView.hideLoading();
    }
    public void loadingTest(){
        mainView.showLoading();

        mainView.toTestActivity();
        mainView.hideLoading();
    }
   
}
