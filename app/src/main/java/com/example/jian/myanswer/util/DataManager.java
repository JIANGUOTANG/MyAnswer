package com.example.jian.myanswer.util;
import android.util.Log;

import com.example.jian.myanswer.bean.CollectQuestion;
import com.example.jian.myanswer.bean.ErrorQuestion;
import com.example.jian.myanswer.bean.FileInfo;
import com.example.jian.myanswer.bean.Question;
import com.example.jian.myanswer.greendao.CollectQuestionDao;
import com.example.jian.myanswer.greendao.DaoSession;
import com.example.jian.myanswer.greendao.ErrorQuestionDao;
import com.example.jian.myanswer.greendao.FileInfoDao;
import com.example.jian.myanswer.greendao.QuestionDao;
import com.example.jian.myanswer.word.App;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by jian on 2017/2/22.
 */
public class DataManager {
    private static QuestionDao questionDao;
    private static DaoSession daoSession;
    private static ErrorQuestionDao errorQuestionDao;
    private static CollectQuestionDao collectQuestionDao;
    private static Query<Question> questionQuery;
    private static Query<ErrorQuestion> errorQuestionQuery;
    private static List<Question> questions;//全部题目
    private static List<Question> mErrorquestions;//做错题目
    private static List<Question> collectQuestions;//收藏的项目
    private static Query<CollectQuestion> collectQuestionQuery;
    public static List<Question> getPraticeQuestions() {
        return questions;
    }
    public static List<Question> getmErrorquestions() {
        return mErrorquestions;
    }
    private static List<ErrorQuestion> errorQuestions;//做错题目的id信息


    private static FileInfoDao fileInfoDao;
    public DataManager() {
        daoSession = App.getDaoSession();
        questionDao = daoSession.getQuestionDao();
        errorQuestionDao = daoSession.getErrorQuestionDao();
        questionQuery = questionDao.queryBuilder().where(QuestionDao.Properties.FileName.eq("test")).orderAsc(QuestionDao.Properties.Id).build();
        errorQuestionQuery = errorQuestionDao.queryBuilder().orderAsc(ErrorQuestionDao.Properties.Id).build();
        collectQuestionDao = daoSession.getCollectQuestionDao();
        collectQuestionQuery = collectQuestionDao.queryBuilder().orderAsc(CollectQuestionDao.Properties.Id).build();
        fileInfoDao = daoSession.getFileInfoDao();
    }
    public static List<Question> getCollectQuestions() {
        Log.i("jianguotang",collectQuestions.size()+"");
        return collectQuestions;
    }

    public static void initCollectQuestion() {
        collectQuestions = new ArrayList<>();
        List<CollectQuestion> questions = collectQuestionQuery.list();
        for (CollectQuestion collectQuestion : questions) {
            Question question = questionDao.load(collectQuestion.getQuestionID());
            collectQuestions.add(question);
        }
    }

    public void initQuestions() {
        questions = questionQuery.list();

    }

    public void initmErrorQuestion() {
        mErrorquestions = new ArrayList<>();
        initErrorQuestion();
        for (ErrorQuestion errorQuestion : errorQuestions) {
            Question question = questionDao.load(errorQuestion.getQuesiontId());
            mErrorquestions.add(question);
        }
    }

    private void initErrorQuestion() {
        errorQuestions = errorQuestionQuery.list();
    }
    public static void addQuestion(Question question) {
        questionDao.insertOrReplace(question);
        questions.add(question);
    }
    public static void upDateQuestoin(Question question){
        questionDao.update(question);
    }
    public static void addCollectQuestion(Question question) {
        collectQuestion = new CollectQuestion(null,question.getId());
        collectQuestionDao.insertOrReplace(collectQuestion);
        collectQuestions.add(question);
        upDateQuestoin(question);
    }
    static CollectQuestion collectQuestion;
    public static void delectCollectQuestion(Question question) {
            collectQuestion = collectQuestionDao.queryBuilder().where(CollectQuestionDao.Properties.QuestionID.eq(question.getId())).build().unique();
            collectQuestionDao.deleteByKey(collectQuestion.getId());
             collectQuestions.remove(question);
            upDateQuestoin(question);

    }
    public static void addErrQuestion(Question question, ErrorQuestion errorQuestion) {
        mErrorquestions.add(question);
        errorQuestionDao.insertOrReplace(errorQuestion);
    }

    public static boolean getFile(String file){
        return  !(fileInfoDao.queryBuilder().where(FileInfoDao.Properties.FileName.eq(file)).build().unique()==null);

    }
    public static void addFile(String fileName){
        FileInfo fileInfo  =  new FileInfo(null,fileName);
        fileInfoDao.insertOrReplace(fileInfo);
    }
    public static  List<Question> getQuestionByFileName(String filename){
        Query<Question> build = questionDao.queryBuilder().where(QuestionDao.Properties.FileName.eq(filename)).build();
        List<Question> list = build.list();
        return  list;
    }
}
