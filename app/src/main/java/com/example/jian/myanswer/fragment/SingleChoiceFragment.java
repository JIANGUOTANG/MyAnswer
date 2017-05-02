package com.example.jian.myanswer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.adapter.SingleChoiceAdapter;
import com.example.jian.myanswer.bean.program.Item;
import com.example.jian.myanswer.view.ProgramView;

/**
 * Created by jian on 2017/4/14.
 */

public class SingleChoiceFragment extends ChoiceFragment {
    private View v;
    private SingleChoiceAdapter singleChoiceAdapter;

    public SingleChoiceFragment(Item item, Context context, ProgramView programView) {
        super(item, context, programView);
    }

    @Override
    View initCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_singlechoice, container, false);
        initEvent();
        if (item.getAnswer().equals(item.getResponse())){

        }
        if(item.getAnswer().equals("")){
            getAnswer();
        }

        return v;
    }
    private void initEvent() {
        singleChoiceAdapter = new SingleChoiceAdapter(item,initOptions(),getActivity());
        singleChoiceAdapter.setOnChoiceChangeListener(new SingleChoiceAdapter.OnChoiceChangeListener() {
            @Override
            public void onChoice(int position,View v) {
                 char answer = (char) (position+'A');
                //设置其他按钮不可以点击
                singleChoiceAdapter.setOnClickAble(false);
                 if (item.getAnswer().trim().equals(answer+"")){
                     v.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorGreen));
                     programView.toNextPager();
                 }else{
                     v.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.spark_secondary_color));
                 }
                 item.setResponse(answer+"");
            }
        });
        initReclerView(singleChoiceAdapter);
    }
    public void getAnswer(){
//        getAnswerRunnable.start();

    }

}
