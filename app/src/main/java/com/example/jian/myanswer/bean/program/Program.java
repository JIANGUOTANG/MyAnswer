package com.example.jian.myanswer.bean.program;

/**
 * Created by jian on 2017/4/15.
 */
public class Program {
    private String time;//上传时间
    private String author;//作者
    private String title;//试卷名称
    private Contents content;
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Contents getContent() {
        return content;
    }

    public void setContent(Contents content) {
        this.content = content;
    }

    public Program(String time, String author, String title, Contents content) {
        this.time = time;
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
