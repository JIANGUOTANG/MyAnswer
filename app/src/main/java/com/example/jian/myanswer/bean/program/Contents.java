package com.example.jian.myanswer.bean.program;

import java.util.List;

/**
 * Created by jian on 2017/4/15.
 */

public class Contents {
    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    private List<Items> items;

    public Contents(List<Items> items) {
        this.items = items;
    }
}
