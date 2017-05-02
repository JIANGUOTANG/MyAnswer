package com.example.jian.myanswer.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.jian.myanswer.bean.Question;
import com.example.jian.myanswer.util.DataManager;
import com.example.jian.myanswer.word.ChoiceResolve;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by jian on 2017/3/19.
 */
public class PiacticeModelImpl implements PracticeModel {
    private List<Question> questions;
    public List<Question> getQuestions() {
        return questions;
    }
    private String QuestionName;
    private String QuestionAnswer;
    private Context context;
    @Override
    public void loadPractice(Context context) {
        this.context = context;
        questions = DataManager.getPraticeQuestions();
        if (isFirstOpen()) {//只执行一次
            QuestionName = openTxt("word/test.txt");
            QuestionAnswer = openTxt("word/testAnswer.txt");
        }
        if (questions.size() != 0 && !isFirstOpen()) {

        } else if (isFirstOpen() | questions.size() < 100) {
            QuestionName = openTxt("word/test.txt");
            QuestionAnswer = openTxt("word/testAnswer.txt");
            new ChoiceResolve(QuestionName, QuestionAnswer).getQuesion();
            questions = DataManager.getPraticeQuestions();
        }

    }
    private String openTxt(String fileName) {
        InputStream is = null;
        String result = "";
        try {
            is = context.getAssets().open(fileName);

            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            result = new String(buffer, "unicode");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 判断是否第一次打开
     *
     * @return
     */
    private boolean isFirstOpen() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("practice", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        boolean isFristOpen = sharedPreferences.getBoolean("practiceFirstOpen", true);

        if (isFristOpen) {
            editor.putBoolean("practiceFirstOpen", false);
            editor.commit();
        }
        return isFristOpen;

    }
}
