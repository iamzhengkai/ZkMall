package com.zk.ec.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Administrator on 2017/12/11.
 */

public class DatabaseManager {
    private DaoSession mDaoSession;
    private UserProfileDao mUserProfileDao;

    private DatabaseManager(){
    }
    private static final class Holder{
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    public static DatabaseManager getInstance(){
        return Holder.INSTANCE;
    }
    public DatabaseManager init(Context context){
        initDao(context);
        return this;
    }
    private void initDao(Context context){
        ReleaseOpenHelper openHelper = new ReleaseOpenHelper(context,"fast_ec.db");
        Database db = openHelper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mUserProfileDao = mDaoSession.getUserProfileDao();
    }

    public final UserProfileDao getUserProfileDao(){
        return mUserProfileDao;
    }
}
