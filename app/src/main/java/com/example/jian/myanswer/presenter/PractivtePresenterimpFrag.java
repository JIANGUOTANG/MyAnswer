package com.example.jian.myanswer.presenter;

import android.widget.LinearLayout;

import com.example.jian.myanswer.view.PracticeView;

/**
 * Created by jian on 2017/2/4.
 */

public class PractivtePresenterimpFrag implements PractivePresenter {
    private PracticeView practiceView;
    private String CorrectAnswer;


    public PractivtePresenterimpFrag(String correctAnswer, PracticeView practiceView) {
        CorrectAnswer = correctAnswer;
        this.practiceView  = practiceView;

    }

    @Override
    public void loadPractive() {

    }

    @Override
    public void showPractice() {

    }

    @Override
    public void onClickA(LinearLayout v) {
        AnswerJudge("A",v);
    }

    @Override
    public void onClickB(LinearLayout v) {
        AnswerJudge("B",v);
    }

    @Override
    public void onClickC(LinearLayout v) {
        AnswerJudge("C",v);
    }

    @Override
    public void onClickD(LinearLayout v) {
        AnswerJudge("D",v);
    }
    private void AnswerJudge(String choice,LinearLayout v){
        if( isCorrect(choice)){
            practiceView.showCorrectColor(v);
            practiceView.toNextQuestion();
            practiceView.showAnswer();
        }
        else{
            practiceView.showErrorColor( v);
            practiceView.showAnswer();
        }
        practiceView.upDateDB(choice);
    }
    public boolean isCorrect(String choice){
        if(CorrectAnswer.contains(choice)){
            return true;
        }
        else{
            return false;
        }
    }

}
