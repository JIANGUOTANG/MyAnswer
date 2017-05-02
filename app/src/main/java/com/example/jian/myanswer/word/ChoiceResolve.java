package com.example.jian.myanswer.word;

import com.example.jian.myanswer.bean.Question;
import com.example.jian.myanswer.util.DataManager;
import com.example.jian.myanswer.util.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 *测试用先
 * Created by jian on 2017/2/5.
 */

public class ChoiceResolve {
    private String strName;//
    private String strAnswer;
    public ChoiceResolve(String str, String strAnswer) {
        this.strName = str;
        this.strAnswer = strAnswer;
    }
    /**
     * 获取选择题
     * @return
     */
    public void getQuesion(){
        List<Question> questions = new ArrayList<>();
        String[] listName = strName.split("\n");//题目和选项
        String [] listAnswer = strAnswer.split("\n");//正确的答案和解析
        for(int i =0,k=0,j = listName.length;i<=j-5;i=i+5,k++){
            String name = listName[i];//题目
            String questionA = listName[i+1],questionB = listName[i+2],questionC = listName[i+3],questionD = listName[i+4];
            String itemAnswer = listAnswer[k];
            String Answer = itemAnswer.substring(itemAnswer.indexOf(".")-1,itemAnswer.indexOf(".")).trim();//答案
            String analysis = itemAnswer.substring(itemAnswer.indexOf(".")+1);//分析
            Question question = new Question(null,name,questionA,questionB,questionC,questionD,Answer,"", analysis,0,0, Tag.TAG_OFFICER,"test");
            DataManager.addQuestion(question);
        }
    }
}
