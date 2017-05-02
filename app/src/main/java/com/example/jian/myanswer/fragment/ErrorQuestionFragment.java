package com.example.jian.myanswer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.adapter.ErrorQuestionAdapter;
import com.example.jian.myanswer.bean.Question;
import com.example.jian.myanswer.util.DataManager;
import com.example.jian.myanswer.view.ErrorQuestionView;
import com.example.jian.myanswer.view.MainView;

import java.util.List;
/**
 * Created by jian on 2017/2/21.
 */
public class ErrorQuestionFragment  extends Fragment implements ErrorQuestionView{
    private View v;
    private RecyclerView recyclerView;
    private List<Question>questions;
    private MainView mainView;
    private ErrorQuestionAdapter errorQuestionAdapter;
    private TextView tvTip;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.actiivty_error_question,container,false);
        initView();
        initData();
        return  v;
    }
    private void initData() {
        questions = DataManager.getmErrorquestions();
        if(questions.size()>0){
            if(tvTip.getVisibility()==View.VISIBLE){
                tvTip.setVisibility(View.GONE);
            }
        }
        errorQuestionAdapter = new ErrorQuestionAdapter(questions,getContext(),this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(errorQuestionAdapter);
    }
    private void initView() {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewEQuestion);
        tvTip = (TextView)v.findViewById(R.id.tvTipError);
    }
    @Override
    public void toQuetionActivity(int position) {
          mainView.toErrorQuestionActivity(position);
    }
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
        if(questions.size()>0){
            if(tvTip.getVisibility()==View.VISIBLE){
                tvTip.setVisibility(View.GONE);
            }
        }
        errorQuestionAdapter.notifyDataSetChanged();
    }
}
