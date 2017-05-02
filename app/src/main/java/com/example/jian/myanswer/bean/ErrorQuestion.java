package com.example.jian.myanswer.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jian on 2017/2/2.
 */
@Entity
public class ErrorQuestion {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private Long QuesiontId;
    @Generated(hash = 235742502)
    public ErrorQuestion(Long id, Long QuesiontId) {
        this.id = id;
        this.QuesiontId = QuesiontId;
    }
    @Generated(hash = 786219436)
    public ErrorQuestion() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getQuesiontId() {
        return this.QuesiontId;
    }
    public void setQuesiontId(Long QuesiontId) {
        this.QuesiontId = QuesiontId;
    }
  


}
