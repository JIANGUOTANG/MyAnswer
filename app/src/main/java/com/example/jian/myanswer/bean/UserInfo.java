package com.example.jian.myanswer.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jian on 2017/3/16.
 */
@Entity
public class UserInfo {
    private String username;
    private int errorCount;//做错的题目数量
    private int correctCount;//做对的题目数量
    @Generated(hash = 1571514924)
    public UserInfo(String username, int errorCount, int correctCount) {
        this.username = username;
        this.errorCount = errorCount;
        this.correctCount = correctCount;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public int getErrorCount() {
        return this.errorCount;
    }
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
    public int getCorrectCount() {
        return this.correctCount;
    }
    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }
}
