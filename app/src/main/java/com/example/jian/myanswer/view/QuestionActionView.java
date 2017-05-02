package com.example.jian.myanswer.view;

import com.example.jian.myanswer.bean.Question;

import java.util.List;

/**
 * Created by jian on 2017/3/31.
 */

public interface QuestionActionView {
    void toNextQuestion();
    void setData(List<Question> data);
}
