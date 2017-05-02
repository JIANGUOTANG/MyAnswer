package com.example.jian.myanswer.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jian on 2017/4/29.
 * 一张试卷的json格式
 */
@Entity
public class JsonProGram {
    @Id(autoincrement = true)
    private Long id;
    @Index(unique = true)
    private String name;//试卷名称
    private String type;//分类
    private String programJson;//试卷内容，json格式
    @Generated(hash = 1705659345)
    public JsonProGram(Long id, String name, String type, String programJson) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.programJson = programJson;
    }
    @Generated(hash = 228621259)
    public JsonProGram() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getProgramJson() {
        return this.programJson;
    }
    public void setProgramJson(String programJson) {
        this.programJson = programJson;
    }
   
}
