package com.example.jian.myanswer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.bean.program.Item;
import com.example.jian.myanswer.view.MyCircleRadioButton;

/**
 * Created by jian on 2017/4/24.
 */

public class CheckingFragment extends Fragment{
    private View v;
    private LinearLayout errorChoice;
    private LinearLayout RightChoice;

    private MyCircleRadioButton myCircleRadioButtonError;//×的图标
    private MyCircleRadioButton myCircleRadioButtonRight;//√的图标
    private Item item;

    public CheckingFragment(Item item) {
        this.item = item;
    }
    private TextView tvChoiceQuestion;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_checking,container,false);
        initView();
        initEvent();
        initData();
        return v;
    }

    private void initData() {
        tvChoiceQuestion.setText(item.getQuestion());
    }

    private void initEvent() {
        errorChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RightChoice.setClickable(false);
            }
        });
        RightChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorChoice.setClickable(false);
            }
        });
    }

    private void initView() {
        myCircleRadioButtonError = (MyCircleRadioButton) v.findViewById(R.id.mCircleCheckBoxError);
        myCircleRadioButtonRight = (MyCircleRadioButton) v.findViewById(R.id.mCircleCheckBoxRight);
        errorChoice = (LinearLayout) v.findViewById(R.id.errorChoice);
        RightChoice = (LinearLayout) v.findViewById(R.id.RightChoice);
        tvChoiceQuestion = (TextView) v.findViewById(R.id.tvChoiceQuestion);
    }
}
