package com.example.jian.myanswer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jian.myanswer.R;
import com.example.jian.myanswer.adapter.MultipleChoiceAdapter;
import com.example.jian.myanswer.bean.program.Item;
import com.example.jian.myanswer.view.ProgramView;

import java.util.Map;

/**
 * Created by jian on 2017/4/13.
 */

public class MultipleChoiceFragment  extends ChoiceFragment {

    private View v;
    private MultipleChoiceAdapter multipleChoiceAdapter;


    public MultipleChoiceFragment(Item item, Context context, ProgramView programView) {
        super(item, context, programView);
    }

    @Override
    View initCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_multiplechoice, container, false);
        initEvent();
        return v;
    }
    private Map<String,Integer> integerMap;
    private void initEvent() {
        multipleChoiceAdapter = new MultipleChoiceAdapter(initOptions());
        multipleChoiceAdapter.setOnChoiceChangeListener(new MultipleChoiceAdapter.OnChoiceChangeListener() {
            @Override
            public void onChecked(boolean isChecked, int position) {
                if(isChecked){
                    integerMap.put(position+"",position);
                }else{
                    integerMap.remove(position+"");
                }
            }
        });
        initReclerView(multipleChoiceAdapter);
    }

}
