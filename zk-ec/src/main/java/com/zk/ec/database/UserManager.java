package com.zk.ec.database;

import com.zk.ec.bean.User;
import com.zk.zkcore.util.log.LoggerCompat;

import java.util.List;

/**
 * Created by Administrator on 2017/12/11.
 */

public class UserManager {
    public static void saveUser(User user) {
        Long id = Long.valueOf(user.getObjectId(), 16);
        LoggerCompat.d(id);
        UserProfile userProfile = new UserProfile(id,user.getObjectId(),user.getUsername(),null,null,null);
        DatabaseManager.getInstance().getUserProfileDao().insert(userProfile);
    }

    public static List<UserProfile> getUsers(){
        List<UserProfile> list = DatabaseManager.getInstance().getUserProfileDao().queryBuilder().build().list();
        for (UserProfile profile : list) {
            LoggerCompat.d(profile);
        }
        return list;
    }
}
