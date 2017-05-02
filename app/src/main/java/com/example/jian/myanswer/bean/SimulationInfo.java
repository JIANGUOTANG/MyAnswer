package com.example.jian.myanswer.bean;

/**
 * Created by jian on 2017/2/10.
 */

public class SimulationInfo {
    private Long id;
    private String name;

    public SimulationInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
