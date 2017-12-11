package com.zk.zkcore.app;

import com.zk.zkcore.util.SPUtils;

/**
 * Created by Administrator on 2017/12/11.
 */

public class AccountManager {
    private enum SignTag{
        SIGN_TAG
    }

    public static void setSignState(boolean state){
        SPUtils.putBoolean(SignTag.SIGN_TAG.name(),state);
    }

    public static boolean isSignIn(){
        return SPUtils.getBoolean(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker checker){
        if (isSignIn()){
            checker.onSignIn();
        }else {
            checker.onNotSignIn();
        }
    }
}
