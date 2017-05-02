package com.example.jian.myanswer.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jian on 2017/2/26.
 */
@Entity
public class Question {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String questionName;//试题题目
    public String optionA; // 正确答案A
    public String optionB; // 正确答案B
    public String optionC; // 正确答案C
    public String optionD; // 正确答案D
    private String correctAnswer;//正确答案
    private String choiceAnswer;//选择的答案，默认是空的
    private String analysis; // 试题分
    private int collected;//是否被收藏了 //0没有，1是
    private int QuestionType;//试题风格 //0选择题，1操作题
    @Index
    private int tag;//用于标记是什么类型的题目，如wps还是ps
    @Index
    private String  fileName;//文件名
    @Generated(hash = 1769114137)
    public Question(Long id, String questionName, String optionA, String optionB,
            String optionC, String optionD, String correctAnswer,
            String choiceAnswer, String analysis, int collected, int QuestionType,
            int tag, String fileName) {
        this.id = id;
        this.questionName = questionName;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.choiceAnswer = choiceAnswer;
        this.analysis = analysis;
        this.collected = collected;
        this.QuestionType = QuestionType;
        this.tag = tag;
        this.fileName = fileName;
    }
    @Generated(hash = 1868476517)
    public Question() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getQuestionName() {
        return this.questionName;
    }
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
    public String getOptionA() {
        return this.optionA;
    }
    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }
    public String getOptionB() {
        return this.optionB;
    }
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }
    public String getOptionC() {
        return this.optionC;
    }
    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }
    public String getOptionD() {
        return this.optionD;
    }
    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }
    public String getCorrectAnswer() {
        return this.correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public String getChoiceAnswer() {
        return this.choiceAnswer;
    }
    public void setChoiceAnswer(String choiceAnswer) {
        this.choiceAnswer = choiceAnswer;
    }
    public String getAnalysis() {
        return this.analysis;
    }
    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
    public int getCollected() {
        return this.collected;
    }
    public void setCollected(int collected) {
        this.collected = collected;
    }
    public int getQuestionType() {
        return this.QuestionType;
    }
    public void setQuestionType(int QuestionType) {
        this.QuestionType = QuestionType;
    }
    public int getTag() {
        return this.tag;
    }
    public void setTag(int tag) {
        this.tag = tag;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    

}
