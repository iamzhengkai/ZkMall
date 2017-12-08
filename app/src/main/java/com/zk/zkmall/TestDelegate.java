package com.zk.zkmall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zk.zkcore.delegates.MallDelegate;
import com.zk.zkcore.net.RestClient;
import com.zk.zkcore.net.callback.IError;
import com.zk.zkcore.net.callback.IFailure;
import com.zk.zkcore.net.callback.ISuccess;

/**
 * Created by Administrator on 2017/12/8.
 */

public class TestDelegate extends MallDelegate {
    private static final String TAG = "TestDelegate";
    @Override
    public Object setLayout() {
        return R.layout.delegate_test;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testClient();
    }

    private void testClient(){
        RestClient.builder()
                .url("http://mall.520it.com/category")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(TAG, "onSuccess: response = " + response);
                        Toast.makeText(getContext(),response,Toast.LENGTH_SHORT).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFaiure(Throwable throwable) {
                        throwable.printStackTrace();
                        Log.e(TAG, "onFaiure: " + throwable.getMessage());
                        Toast.makeText(getContext(),"Failure",Toast.LENGTH_SHORT).show();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int errorCode, String msg) {
                        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
                    }
                })
                .build()
                .get();
    }
}
