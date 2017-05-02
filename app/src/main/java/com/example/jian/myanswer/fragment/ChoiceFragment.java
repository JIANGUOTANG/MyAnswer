package com.example.jian.myanswer.fragment;

import android.content.Context;
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
import com.example.jian.myanswer.adapter.ChoiceAdapter;
import com.example.jian.myanswer.bean.program.Item;
import com.example.jian.myanswer.view.ProgramView;

import java.util.List;

/**
 * Created by jian on 2017/4/17.
 */

public abstract class ChoiceFragment extends Fragment {

    private View v;
    private RecyclerView recyclerView;
    private TextView quesionName;
     Item item;
    private Context context;
    ProgramView programView;
    public ChoiceFragment(Item item, Context context,ProgramView programView) {
        this.programView = programView;
        this.item = item;
        this.context = context;
    }
    List<String> options;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = initCreateView(inflater,container,savedInstanceState);
        initView();
        initData();
        initEvent();
        return v;
    }
    abstract View initCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
    private void initData() {
        initOptions();
        quesionName.setText(strQuestionName);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

    }
    private void initEvent() {

    }
    private void initView() {
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewChoice);
        quesionName = (TextView) v.findViewById(R.id.tvChoiceQuestion);
        recyclerView.setAdapter(choiceAdapter);
    }
    private String strQuestionName;
    public String[] initOptions() {
        String question = item.getQuestion();//获得选项

        String str[] = question.split("@##@");//把每个选项切分开
        strQuestionName = str[0];

        return str;
    }
    ChoiceAdapter choiceAdapter;
    public void initReclerView(ChoiceAdapter choiceAdapter){
       this.choiceAdapter = choiceAdapter;

    }
//    Main main = new Main() {
//
//        @Override
//        public void MainRespond(String answer, String explain) {
//            Log.i("jianAnswer", answer+"\n"+explain);
//            String regex = "[A-Ha-h]+";
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(answer);
//            if(matcher.find()){
//                Log.i("choicefragm",matcher.group(0));
//                item.setAnswer(matcher.group(0));
//            }
//            item.setAnalyze(explain);
//        }
//    };
//    GetAnswerThread getAnswerRunnable = new GetAnswerThread();
//    class GetAnswerThread extends Thread {
//        private volatile Thread t;
//        public void run() {
//            try {
//                main.selection(strQuestionName);
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        private void mStop(){
//            t = null;
//        }
//        public void start () {
////            if (t == null) {
//            t = new Thread (this);
//            t.start ();
////            }else{
////                interrupt();
////                t.start ();
////
////            }
//        }
//        public void interrupt(){
//            if(!t.isInterrupted()){
//                t.interrupt();
//            }
//        }
//    }


}
