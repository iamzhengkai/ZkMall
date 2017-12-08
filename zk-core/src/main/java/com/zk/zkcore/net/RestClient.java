package com.zk.zkcore.net;

import android.content.Context;

import com.zk.zkcore.net.callback.IError;
import com.zk.zkcore.net.callback.IFailure;
import com.zk.zkcore.net.callback.IRequest;
import com.zk.zkcore.net.callback.ISuccess;
import com.zk.zkcore.net.callback.RequestCallbacks;
import com.zk.zkcore.ui.LoaderStyle;
import com.zk.zkcore.ui.MallLoader;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/12/8.
 */

public class RestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestHolder.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;

    RestClient(String url,
               Map<String, Object> params,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               LoaderStyle loaderStyle,
               Context context
               ) {
        this.URL = url;
        PARAMS.putAll(params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.CONTEXT = context;
    }

    public static RestClientBuilder builder() {
        return new RestClientBuilder();
    }

    private void request(HttpMethod method) {
        final RestService service = RestHolder.getRestService();
        Call<String> call = null;
        if (REQUEST != null) {
            REQUEST.onRequestStart();
        }

        if (LOADER_STYLE != null){
            MallLoader.show(CONTEXT,LOADER_STYLE);
        }
        switch (method) {
            case GET:
                call = service.get(URL, PARAMS);
                break;
            case POST:
                call = service.post(URL, PARAMS);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            default:
                break;
        }

        if (call != null) {
            call.enqueue(new RequestCallbacks(REQUEST, SUCCESS, FAILURE, ERROR,LOADER_STYLE));
        }
    }

    public final void get() {
        request(HttpMethod.GET);
    }

    public final void post() {
        request(HttpMethod.POST);
    }

    public final void put() {
        request(HttpMethod.PUT);
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

}
