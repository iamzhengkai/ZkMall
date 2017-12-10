package com.zk.zkcore.net;

import android.content.Context;
import android.view.ViewGroup;

import com.zk.zkcore.net.callback.IError;
import com.zk.zkcore.net.callback.IFailure;
import com.zk.zkcore.net.callback.IRequest;
import com.zk.zkcore.net.callback.ISuccess;
import com.zk.zkcore.ui.LoaderCreator;
import com.zk.zkcore.ui.LoaderStyle;
import com.zk.zkcore.ui.MallLoader;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/12/8.
 */

public class RestClientBuilder {
    private String mUrl;
    private static final Map<String, Object> PARAMS = RestHolder.getParams();
    private WeakHashMap<String,Object> mHeaders = new WeakHashMap<>();
    private IRequest mRequest;
    private ISuccess mSuccess;
    private IFailure mFailure;
    private IError mError;
    private RequestBody mBody;
    private File mFile;
    private LoaderStyle mLoaderStyle;
    private Context mContext;

    private String mDowloadDir;
    private String mExtension;
    private String mName;

    RestClientBuilder() {

    }

    public final RestClientBuilder url(String url) {
        mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder param(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder header(String key,Object value){
        mHeaders.put(key,value);
        return this;
    }

    public final RestClientBuilder headers(WeakHashMap<String,Object> headers){
        mHeaders.putAll(headers);
        return this;
    }

    public final RestClientBuilder request(IRequest request) {
        mRequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        mSuccess = success;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure) {
        mFailure = failure;
        return this;
    }

    public final RestClientBuilder error(IError error) {
        mError = error;
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style) {
        mContext = context;
        mLoaderStyle = style;
        return this;
    }

    public final RestClientBuilder loader(Context context) {
        mContext = context;
        mLoaderStyle = LoaderStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClientBuilder file(File file) {
        mFile = file;
        return this;
    }

    public final RestClientBuilder file(String filePath) {
        mFile = new File(filePath);
        return this;
    }

    public final RestClientBuilder dir(String dowloadDir) {
        mDowloadDir = dowloadDir;
        return this;
    }

    public final RestClientBuilder extension(String extension) {
        mExtension = extension;
        return this;
    }

    public final RestClientBuilder name(String name) {
        mName = name;
        return this;
    }

    public final RestClient build() {
        return new RestClient(mUrl, PARAMS,mHeaders, mDowloadDir, mExtension, mName,
                mRequest, mSuccess, mFailure, mError, mBody, mFile, mLoaderStyle, mContext);
    }
}
