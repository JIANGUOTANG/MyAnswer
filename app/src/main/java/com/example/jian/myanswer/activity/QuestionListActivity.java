package com.example.jian.myanswer.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.adapter.ErrorQuestionAdapter;
import com.example.jian.myanswer.bean.Question;
import com.example.jian.myanswer.util.DataManager;
import com.example.jian.myanswer.view.ErrorQuestionView;
import com.example.jian.myanswer.view.MainView;

import java.util.List;

/**
 * Created by jian on 2017/3/14.
 */

public abstract class QuestionListActivity extends MyBaseActivity implements ErrorQuestionView{

     RecyclerView recyclerView;
     List<Question> questions;
     MainView mainView;
     ErrorQuestionAdapter errorQuestionAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public  void initData() {
        loadQuestion();
        errorQuestionAdapter = new ErrorQuestionAdapter(questions,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(errorQuestionAdapter);
    }
    public abstract void loadQuestion();
    public void initView() {
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewEQuestion);
    }
    @Override
    public void toQuetionActivity(int position) {
        toQuestionActivity(position);
    }
    public abstract  void toQuestionActivity(int position);
    @Override
    public void showLoad() {

    }
    @Override
    public void  hideLoad(List<Question> questions) {
        this.questions = questions;
    }

    public void setMainView(MainView mainView){
        this.mainView = mainView;
    }
    //刷新数据
    public void reFleshData(){
        questions = DataManager.getmErrorquestions();
        errorQuestionAdapter.notifyDataSetChanged();
    }

}
