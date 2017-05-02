package com.example.jian.myanswer.bean.program;

/**
 * Created by jian on 2017/4/14.
 */

public class Item {
    private String question;
    private String response;//回答的答案
    private String analyze;//分析
    private String answer;//答案

    public Item(String question, String response, String analyze, String answer, String answerFromNet) {
        this.question = question;
        this.response = response;
        this.analyze = analyze;
        this.answer = answer;
        this.answerFromNet = answerFromNet;
    }

    private String answerFromNet;//互联网答案及分析。参考用

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getAnalyze() {
        return analyze;
    }

    public void setAnalyze(String analyze) {
        this.analyze = analyze;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerFromNet() {
        return answerFromNet;
    }

    public void setAnswerFromNet(String answerFromNet) {
        this.answerFromNet = answerFromNet;
    }
}
