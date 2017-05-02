package com.example.jian.myanswer.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jian on 2017/3/7.
 * 题目文件信息，当被打开过的就保存到数据库，下次打开的时候直接从数据库中读取
 */
//
    @Entity
public class FileInfo {
    @Id(autoincrement = true)
    private Long id;
    @Index
    private String  fileName;
    @Generated(hash = 1460523624)
    public FileInfo(Long id, String fileName) {
        this.id = id;
        this.fileName = fileName;
    }
    @Generated(hash = 1367591352)
    public FileInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFileName() {
        return this.fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
