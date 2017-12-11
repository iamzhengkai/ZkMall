package com.zk.ec.sign;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.zk.ec.bean.ResponseError;
import com.zk.ec.bean.User;
import com.zk.ec.database.UserManager;
import com.zk.zkcore.app.AccountManager;
import com.zk.zkcore.util.ToastUtils;
import com.zk.zkcore.util.log.LoggerCompat;

/**
 * Created by Administrator on 2017/12/11.
 */

public class SignHandler {
    public static void onSignUpSuccess(User user, ISignListener signListener){
        UserManager.saveUser(user);
        AccountManager.setSignState(true);
        if (signListener != null){
            signListener.onSignUpSuccess();
        }
    }

    public static void onSignUpError(int errorCode, String msg, String errorBody) {
        LoggerCompat.e("onError: errorBody=" + errorBody);
        if (!TextUtils.isEmpty(errorBody)){
            ResponseError error = JSON.parseObject(errorBody, ResponseError.class);
            if (error != null){
                ToastUtils.showLongToast("注册失败: 错误码: " + error.getCode() + "; 错误提示: " + error.getError());
                return;
            }
        }
        ToastUtils.showLongToast("注册失败: 错误码 = " + errorCode + "; 错误提示: " + msg );
    }

    public static void onSignInSuccess(ISignListener signListener){
        //设置登陆成功标识
        AccountManager.setSignState(true);
        if (signListener != null){
            signListener.onSignInSuccess();
        }
    }

    public static void onSignInError(int errorCode, String msg, String errorBody) {
        LoggerCompat.e("onError: errorBody=" + errorBody);
        if (!TextUtils.isEmpty(errorBody)){
            ResponseError error = JSON.parseObject(errorBody, ResponseError.class);
            if (error != null){
                ToastUtils.showLongToast("登陆失败: 错误码: " + error.getCode() + "; 错误提示: " + error.getError());
                return;
            }
        }
        ToastUtils.showLongToast("登陆失败: 错误码 = " + errorCode + "; 错误提示: " + msg );
    }
}
