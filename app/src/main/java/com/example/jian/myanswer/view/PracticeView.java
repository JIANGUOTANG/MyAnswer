package com.example.jian.myanswer.view;

import android.widget.LinearLayout;

import com.example.jian.myanswer.bean.Question;

import java.util.List;

/**
 * Created by jian on 2017/2/4.
 */

public interface PracticeView {
     void showLoading();//显示正在加载
     void hideLoad();//隐藏正在加载页面
    void setData(List<Question> data);
      void showAnswer();//显示正确答案和提示
     void toNextQuestion();//跳到下一题
     void showErrorColor(LinearLayout v);//选中错误的答案的时候，显示红色
     void showCorrectColor(LinearLayout v);//选中正确答案，显示绿色
     void upDateDB(String choice);

}
