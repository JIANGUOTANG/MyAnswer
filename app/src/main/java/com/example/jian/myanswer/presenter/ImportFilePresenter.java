package com.example.jian.myanswer.presenter;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.jian.myanswer.bean.localFileInfo;
import com.example.jian.myanswer.model.ImportFileModel;
import com.example.jian.myanswer.view.ImportFileView;

/**
 * Created by jian on 2017/4/3.
 */

public class ImportFilePresenter implements ImportFileModel.seachFileListener{
    private ImportFileModel importFileModel;
    private ImportFileView importFileView;
    private final int SEACHFINISHED= 2;
    private final int UPDATELIST= 1;

    public ImportFilePresenter(ImportFileView importFileView) {
        this.importFileModel = new ImportFileModel();
        this.importFileView = importFileView;
        importFileModel.setSeachFileListener(this);
    }
    /**
     * 查询文件
     */
    public void loadFile(){
        importFileModel.setInterrupted(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                importFileModel.seachFile();

            }
        }).start();
    }
    public void toChoiceFileScene(){
        importFileView.toChoiceFileSecne();
    }
    /**
     *  停止查询
     */
    public void stopSeach(){
        importFileModel.setInterrupted(true);
    }
    @Override
    public void upDataList(localFileInfo localFileInfo) {
        Message message = new Message();
        message.obj= localFileInfo;
        message.what = UPDATELIST;
        handler.sendMessage(message);
    }
    @Override
    public void seachFinished() {
          handler.sendEmptyMessage(SEACHFINISHED);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATELIST:
                    localFileInfo localFileInfo = (com.example.jian.myanswer.bean.localFileInfo) msg.obj;
                    importFileView.upDateList(localFileInfo);
                    Log.i("jianname",localFileInfo.getFileName());
                    break;
                case SEACHFINISHED:
                    Log.i("jianguotang","sggggggggg");
                    importFileView.stopAnim();
                    break;
            }

        }
    };
}
