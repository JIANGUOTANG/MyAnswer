package com.example.jian.myanswer.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.jian.myanswer.bean.UserInfo;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER_INFO".
*/
public class UserInfoDao extends AbstractDao<UserInfo, Void> {

    public static final String TABLENAME = "USER_INFO";

    /**
     * Properties of entity UserInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Username = new Property(0, String.class, "username", false, "USERNAME");
        public final static Property ErrorCount = new Property(1, int.class, "errorCount", false, "ERROR_COUNT");
        public final static Property CorrectCount = new Property(2, int.class, "correctCount", false, "CORRECT_COUNT");
    }


    public UserInfoDao(DaoConfig config) {
        super(config);
    }
    
    public UserInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER_INFO\" (" + //
                "\"USERNAME\" TEXT," + // 0: username
                "\"ERROR_COUNT\" INTEGER NOT NULL ," + // 1: errorCount
                "\"CORRECT_COUNT\" INTEGER NOT NULL );"); // 2: correctCount
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(1, username);
        }
        stmt.bindLong(2, entity.getErrorCount());
        stmt.bindLong(3, entity.getCorrectCount());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, UserInfo entity) {
        stmt.clearBindings();
 
        String username = entity.getUsername();
        if (username != null) {
            stmt.bindString(1, username);
        }
        stmt.bindLong(2, entity.getErrorCount());
        stmt.bindLong(3, entity.getCorrectCount());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public UserInfo readEntity(Cursor cursor, int offset) {
        UserInfo entity = new UserInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // username
            cursor.getInt(offset + 1), // errorCount
            cursor.getInt(offset + 2) // correctCount
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, UserInfo entity, int offset) {
        entity.setUsername(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setErrorCount(cursor.getInt(offset + 1));
        entity.setCorrectCount(cursor.getInt(offset + 2));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(UserInfo entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(UserInfo entity) {
        return null;
    }

    @Override
    public boolean hasKey(UserInfo entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
