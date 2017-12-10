package com.zk.zkcore.net.download;

import android.os.AsyncTask;

import com.zk.zkcore.net.RestHolder;
import com.zk.zkcore.net.callback.IError;
import com.zk.zkcore.net.callback.IFailure;
import com.zk.zkcore.net.callback.IRequest;
import com.zk.zkcore.net.callback.ISuccess;
import com.zk.zkcore.net.callback.RequestCallbacks;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/12/9.
 */

public class DownloadHandler {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestHolder.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String URL,
                           IRequest REQUEST,
                           String DOWNLOAD_DIR,
                           String EXTENSION,
                           String NAME,
                           ISuccess SUCCESS,
                           IFailure FAILURE,
                           IError ERROR) {
        this.URL = URL;
        this.REQUEST = REQUEST;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.NAME = NAME;
        this.SUCCESS = SUCCESS;
        this.FAILURE = FAILURE;
        this.ERROR = ERROR;
    }

    public final void handleDownload() {
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        RestHolder.getRestService().download(URL, PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            ResponseBody body = response.body();
                            SaveFileTask saveFileTask = new SaveFileTask(REQUEST, SUCCESS);
                            saveFileTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, body, NAME);
                            //这里一定要注意判断，否则文件会下载不全
                       /* if (saveFileTask.isCancelled()){
                            if (REQUEST != null){
                                REQUEST.onRequestEnd();
                            }
                        }*/
                        }else {
                            if (ERROR !=null){
                                ERROR.onError(response.code(),response.message(),"");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if (FAILURE != null){
                            FAILURE.onFaiure(t);
                        }
                    }
                });

    }
}
