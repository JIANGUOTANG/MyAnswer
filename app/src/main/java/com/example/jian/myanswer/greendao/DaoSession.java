package com.example.jian.myanswer.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.jian.myanswer.bean.CollectQuestion;
import com.example.jian.myanswer.bean.ErrorQuestion;
import com.example.jian.myanswer.bean.FileInfo;
import com.example.jian.myanswer.bean.JsonProGram;
import com.example.jian.myanswer.bean.localFileInfo;
import com.example.jian.myanswer.bean.Question;
import com.example.jian.myanswer.bean.UserInfo;

import com.example.jian.myanswer.greendao.CollectQuestionDao;
import com.example.jian.myanswer.greendao.ErrorQuestionDao;
import com.example.jian.myanswer.greendao.FileInfoDao;
import com.example.jian.myanswer.greendao.JsonProGramDao;
import com.example.jian.myanswer.greendao.localFileInfoDao;
import com.example.jian.myanswer.greendao.QuestionDao;
import com.example.jian.myanswer.greendao.UserInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig collectQuestionDaoConfig;
    private final DaoConfig errorQuestionDaoConfig;
    private final DaoConfig fileInfoDaoConfig;
    private final DaoConfig jsonProGramDaoConfig;
    private final DaoConfig localFileInfoDaoConfig;
    private final DaoConfig questionDaoConfig;
    private final DaoConfig userInfoDaoConfig;

    private final CollectQuestionDao collectQuestionDao;
    private final ErrorQuestionDao errorQuestionDao;
    private final FileInfoDao fileInfoDao;
    private final JsonProGramDao jsonProGramDao;
    private final localFileInfoDao localFileInfoDao;
    private final QuestionDao questionDao;
    private final UserInfoDao userInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        collectQuestionDaoConfig = daoConfigMap.get(CollectQuestionDao.class).clone();
        collectQuestionDaoConfig.initIdentityScope(type);

        errorQuestionDaoConfig = daoConfigMap.get(ErrorQuestionDao.class).clone();
        errorQuestionDaoConfig.initIdentityScope(type);

        fileInfoDaoConfig = daoConfigMap.get(FileInfoDao.class).clone();
        fileInfoDaoConfig.initIdentityScope(type);

        jsonProGramDaoConfig = daoConfigMap.get(JsonProGramDao.class).clone();
        jsonProGramDaoConfig.initIdentityScope(type);

        localFileInfoDaoConfig = daoConfigMap.get(localFileInfoDao.class).clone();
        localFileInfoDaoConfig.initIdentityScope(type);

        questionDaoConfig = daoConfigMap.get(QuestionDao.class).clone();
        questionDaoConfig.initIdentityScope(type);

        userInfoDaoConfig = daoConfigMap.get(UserInfoDao.class).clone();
        userInfoDaoConfig.initIdentityScope(type);

        collectQuestionDao = new CollectQuestionDao(collectQuestionDaoConfig, this);
        errorQuestionDao = new ErrorQuestionDao(errorQuestionDaoConfig, this);
        fileInfoDao = new FileInfoDao(fileInfoDaoConfig, this);
        jsonProGramDao = new JsonProGramDao(jsonProGramDaoConfig, this);
        localFileInfoDao = new localFileInfoDao(localFileInfoDaoConfig, this);
        questionDao = new QuestionDao(questionDaoConfig, this);
        userInfoDao = new UserInfoDao(userInfoDaoConfig, this);

        registerDao(CollectQuestion.class, collectQuestionDao);
        registerDao(ErrorQuestion.class, errorQuestionDao);
        registerDao(FileInfo.class, fileInfoDao);
        registerDao(JsonProGram.class, jsonProGramDao);
        registerDao(localFileInfo.class, localFileInfoDao);
        registerDao(Question.class, questionDao);
        registerDao(UserInfo.class, userInfoDao);
    }
    
    public void clear() {
        collectQuestionDaoConfig.clearIdentityScope();
        errorQuestionDaoConfig.clearIdentityScope();
        fileInfoDaoConfig.clearIdentityScope();
        jsonProGramDaoConfig.clearIdentityScope();
        localFileInfoDaoConfig.clearIdentityScope();
        questionDaoConfig.clearIdentityScope();
        userInfoDaoConfig.clearIdentityScope();
    }

    public CollectQuestionDao getCollectQuestionDao() {
        return collectQuestionDao;
    }

    public ErrorQuestionDao getErrorQuestionDao() {
        return errorQuestionDao;
    }

    public FileInfoDao getFileInfoDao() {
        return fileInfoDao;
    }

    public JsonProGramDao getJsonProGramDao() {
        return jsonProGramDao;
    }

    public localFileInfoDao getLocalFileInfoDao() {
        return localFileInfoDao;
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

}