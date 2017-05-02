package com.example.jian.myanswer.view;

import com.example.jian.myanswer.bean.Question;

import java.util.List;

/**
 * Created by jian on 2017/2/21.
 */

public interface ErrorQuestionView {
     void toQuetionActivity(int position);
     void showLoad();
     void hideLoad(List<Question> questions);
}
