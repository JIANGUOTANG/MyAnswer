package com.example.jian.myanswer.word;

import android.os.Environment;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jian on 2017/4/1.
 */

public class MyFilter implements FilenameFilter {
    private String suffix;

    public MyFilter(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean accept(File dir, String name) {
        return (name.endsWith(suffix));

    }
    public  List<Map<String, String>>  fileList() {
        //取得需要遍历的文件目录
        File home = Environment.getExternalStorageDirectory();
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        //遍历文件目录
        if (home.listFiles(this).length > 0) {
            for (File file : home.listFiles(this)) {
                Map<String, String> map = new HashMap<String, String>();
                System.out.println("musicName is: " + file.getName());
                map.put("fileName", file.getName());
                list.add(map);
            }
        }
        return list;
    }
}
