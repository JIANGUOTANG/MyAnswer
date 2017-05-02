package com.example.jian.myanswer.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jian on 2017/2/26.
 */
@Entity

public class CollectQuestion {

    @Id(autoincrement = true)
    private Long id;
    @Unique
    private Long QuestionID;
    @Generated(hash = 321463276)
    public CollectQuestion(Long id, Long QuestionID) {
        this.id = id;
        this.QuestionID = QuestionID;
    }
    @Generated(hash = 479203823)
    public CollectQuestion() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getQuestionID() {
        return this.QuestionID;
    }
    public void setQuestionID(Long QuestionID) {
        this.QuestionID = QuestionID;
    }
}
