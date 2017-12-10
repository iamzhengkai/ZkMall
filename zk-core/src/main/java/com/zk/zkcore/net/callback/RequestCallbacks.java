package com.zk.zkcore.net.callback;

import android.os.Handler;

import com.zk.zkcore.ui.LoaderStyle;
import com.zk.zkcore.ui.MallLoader;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/8.
 */

public class RequestCallbacks implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final LoaderStyle LOADER_STYLE;
    private static Handler HANDLER = new Handler();

    public RequestCallbacks(IRequest request, ISuccess success, IFailure failure, IError error,LoaderStyle loaderStyle) {
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.LOADER_STYLE = loaderStyle;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            if (call.isExecuted()){
                if (SUCCESS != null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        }else {
            if (ERROR != null){
                String errorBody = "";
                if (response.errorBody() != null){
                    try {
                        errorBody = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                        errorBody = "";
                    }
                }
                ERROR.onError(response.code(),response.message(),errorBody);
            }
        }

        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }

        stopLoading();
    }

    private void stopLoading() {
        if (LOADER_STYLE != null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MallLoader.dismiss();
                }
            },2000);

        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if (FAILURE != null){
            FAILURE.onFaiure(t);
        }

        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }

        stopLoading();
    }
}
