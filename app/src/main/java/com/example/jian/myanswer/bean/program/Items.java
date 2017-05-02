package com.example.jian.myanswer.bean.program;

import java.util.List;

/**
 * Created by jian on 2017/4/14.
 */

public class Items {
    private int type;//题目类型
    private int location;//标志是第几道大题，1,2,3,4,5,
    private List<Item> item;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Item> getItem() {
        return item;
    }

    public Items(int type, int location, List<Item> item) {
        this.type = type;
        this.location = location;
        this.item = item;
    }

    public int getLocation() {

        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setItem(List<Item> item) {
        this.item = item;
    }

}
