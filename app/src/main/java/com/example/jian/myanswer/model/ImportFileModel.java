package com.example.jian.myanswer.model;

import android.os.Environment;
import android.util.Log;

import com.example.jian.myanswer.bean.localFileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jian on 2017/4/3.
 */

public class ImportFileModel {
    private List<localFileInfo>fileInfos = new ArrayList<>();
    public void setInterrupted(boolean interrupted) {
        isInterrupted = interrupted;
    }
   private  seachFileListener seachFileListener;
    private boolean isInterrupted = false;//判断是否被中断了查询
    public void seachFile(){//查询手机中的文件
        filePath(Environment.getExternalStorageDirectory());
        seachFileListener.seachFinished();
    }
    /**
     * 查询全部文件，查找适合的文件
     * @param file
     */
    public void filePath(File file){
        if(file!=null&&file.exists()&&file.isDirectory()&&file.canRead()&&!isInterrupted){
            File[] files = file.listFiles();
            for(File file2 :files){
                if(file2.listFiles()==null){
                    String regex = "\\.doc.{0,1}|\\.txt";//查找txt,docx的文件
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(file2.getName());
                    if(matcher.find()&&!file2.getName().contains("log")){
//                    }
//                    if(file2.getName().contains(".docx")||file2.getName().contains(".doc")||file2.getName().contains(".txt")){
                        String fileName = file2.getName();//获得文件名字
                        String filePath = file2.getAbsolutePath();//获得文件路径
                        localFileInfo localFileInfo = new localFileInfo(null,fileName,filePath);
                        fileInfos.add(localFileInfo);
                        if(seachFileListener!=null){
                            seachFileListener. upDataList(localFileInfo);
                        }
                    }
                }
                else{
                    filePath(file2);
                }
            }
        }else{
            Log.i("jianddddddd","文件不存在");
        }

    }

    public void setSeachFileListener(ImportFileModel.seachFileListener seachFileListener) {
        this.seachFileListener = seachFileListener;
    }

    public interface seachFileListener{
        void upDataList(localFileInfo localFileInfo);//更新文件列表
        void seachFinished();//完成查询
    }
}
