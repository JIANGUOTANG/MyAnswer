package com.example.jian.myanswer.word;

import android.app.Application;

import com.example.jian.myanswer.greendao.DaoMaster;
import com.example.jian.myanswer.greendao.DaoSession;
import com.example.jian.myanswer.util.DataManager;

import org.greenrobot.greendao.database.Database;

/**
 * Created by jian on 2017/2/2.
 */
public class App extends Application {

    public static final boolean ENCRYPTED = true;

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new  DaoMaster.DevOpenHelper(this, ENCRYPTED ? "users-db-encrypted" : "myusers-db");
        Database db =  helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
        intData();
    }
    public static DaoSession getDaoSession() {
        return daoSession;
    }
    public void intData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DataManager dataManager  = new DataManager();
                dataManager.initQuestions();
                dataManager.initmErrorQuestion();
                dataManager.initCollectQuestion();
            }
        }).start();

    }
}