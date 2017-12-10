package com.zk.zkcore.net.callback;


import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/8.
 */

public interface IError {
//    void onError(int errorCode,String msg);
    void onError(int errorCode, String msg, String errorBody);
}
