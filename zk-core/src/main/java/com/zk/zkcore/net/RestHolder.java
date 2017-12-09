package com.zk.zkcore.net;

import com.zk.zkcore.app.ConfigType;
import com.zk.zkcore.app.Core;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/12/8.
 */

public class RestHolder {
    private RestHolder(){}

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }

    public static OkHttpClient getOkhttpClient(){
        return OkHttpHolder.OK_HTTP_CLIENT;
    }

    public static Retrofit getRetrofitClient(){
        return RetrofitHolder.RETROFIT_CLIENT;
    }

    private static final class ParamsHolder{
        private static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS;
    }

    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) Core.getConfigurations().get(ConfigType.API_HOST.name());
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OkHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
