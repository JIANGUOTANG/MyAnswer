package com.example.jian.myanswer.presenter;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.jian.myanswer.model.PiacticeModelImpl;
import com.example.jian.myanswer.view.QuestionActionView;

/**
 * Created by jian on 2017/2/4.
 */

public class PractivtePresenterimp implements PractivePresenter {
    private QuestionActionView practiceView;

    private Context context;
       private PiacticeModelImpl practiceModel;
    public PractivtePresenterimp(QuestionActionView practiceView,Context context) {

        this.practiceView  = practiceView;
        this.context = context;
        practiceModel = new PiacticeModelImpl();
    }

    @Override
    public void loadPractive() {
        practiceModel.loadPractice(context);
    }

    @Override
    public void showPractice() {
        practiceView.setData(practiceModel.getQuestions());
    }

    @Override
    public void onClickA(LinearLayout v) {

    }

    @Override
    public void onClickB(LinearLayout v) {

    }

    @Override
    public void onClickC(LinearLayout v) {

    }

    @Override
    public void onClickD(LinearLayout v) {

    }



}
