package com.example.jian.myanswer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.bean.program.Item;

/**
 * Created by jian on 2017/2/13.
 */

public class OperationQuesiontFragment extends Fragment{
   private TextView tvQuestion;
   private Item item;
    public OperationQuesiontFragment(Item item) {
        this.item = item;
    }
    private View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_operation,container,false);
        initView();
        initData();
        return v;

    }

    private void initData() {
        tvQuestion.setText(item.getQuestion());
    }

    private void initView() {
        tvQuestion = (TextView) v.findViewById(R.id.tvQuestion);
    }
}
