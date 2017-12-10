package com.zk.zkcore.net;

import android.content.Context;

import com.zk.zkcore.net.callback.IError;
import com.zk.zkcore.net.callback.IFailure;
import com.zk.zkcore.net.callback.IRequest;
import com.zk.zkcore.net.callback.ISuccess;
import com.zk.zkcore.net.callback.RequestCallbacks;
import com.zk.zkcore.net.download.DownloadHandler;
import com.zk.zkcore.ui.LoaderStyle;
import com.zk.zkcore.ui.MallLoader;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2017/12/8.
 */

public class RestClient {
    private final String URL;
    private static final WeakHashMap<String, Object> PARAMS = RestHolder.getParams();
    private final WeakHashMap<String,Object> HEADERS = new WeakHashMap<>();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context CONTEXT;
    private final File FILE;

    RestClient(String url,
               Map<String, Object> params,
               Map<String,Object> headers,
               String downloadDir,
               String extension,
               String name,
               IRequest request,
               ISuccess success,
               IFailure failure,
               IError error,
               RequestBody body,
               File file,
               LoaderStyle loaderStyle,
               Context context
               ) {
        this.URL = url;
        PARAMS.putAll(params);
        HEADERS.putAll(headers);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.LOADER_STYLE = loaderStyle;
        this.CONTEXT = context;
        this.FILE = file;
        this.DOWNLOAD_DIR = downloadDir;
        this.EXTENSION = extension;
        this.NAME = name;
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
            case POST_RAW:
                call = service.postRaw(URL,BODY);
                break;
            case POST_RAW_WITH_HEADER:
                call = service.postRaw(URL,HEADERS,BODY);
                break;
            case PUT:
                call = service.put(URL, PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
                break;
            case DELETE:
                call = service.delete(URL, PARAMS);
                break;
            case UPLOAD:
                RequestBody requestBody = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
//                MultipartBody.Part part = MultipartBody.Part.createFormData("file",FILE.getName());
                MultipartBody.Part part = MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call = service.upload(URL,part);
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
        if (BODY == null){
            request(HttpMethod.POST);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("params must be empty! Try to post a raw message, but params is not empty!");
            }
            if (HEADERS.isEmpty()){
                request(HttpMethod.POST_RAW);
            }else {
                request(HttpMethod.POST_RAW_WITH_HEADER);
            }
        }
    }

    public final void put() {
        if (BODY == null){
            request(HttpMethod.PUT);
        }else {
            if (!PARAMS.isEmpty()){
                throw new RuntimeException("Params must be empty! Try to put a raw message, but params is not empty!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }

    public final void delete() {
        request(HttpMethod.DELETE);
    }

    public final void upload(){
        request(HttpMethod.UPLOAD);
    }


    public final void download(){
        new DownloadHandler(URL,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,FAILURE,ERROR).handleDownload();
    }
}
