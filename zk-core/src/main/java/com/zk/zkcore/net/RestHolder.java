package com.zk.zkcore.net;

import com.zk.zkcore.app.ConfigType;
import com.zk.zkcore.app.ConfigurationManager;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2017/12/8.
 */

public class RestHolder {
    private RestHolder() {
    }

    public static RestService getRestService() {
        return RestServiceHolder.REST_SERVICE;
    }

    public static OkHttpClient getOkhttpClient() {
        return OkHttpHolder.OK_HTTP_CLIENT;
    }

    public static Retrofit getRetrofitClient() {
        return RetrofitHolder.RETROFIT_CLIENT;
    }

    //PARAMS 不能是全局的，否则所有请求的请求参数会混在一起
  /*  private static final class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }*/

    /*public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }*/

    private static final class RetrofitHolder {
        private static final String BASE_URL = (String) ConfigurationManager.getConfigurations().get(ConfigType.API_HOST);
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OkHttpHolder {
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS =
                ConfigurationManager.getConfiguration(ConfigType.INTERCEPTOR);
        private static final ArrayList<Interceptor> NETWORK_INTERCEPTORS =
                ConfigurationManager.getConfiguration(ConfigType.NETWORK_INTERCEPTOR);

        private static OkHttpClient.Builder addInterceptors(){
            if (INTERCEPTORS != null && !INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor : INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            addNetworkInterceptors();
            return BUILDER;
        }

        private static OkHttpClient.Builder addNetworkInterceptors(){
            if (NETWORK_INTERCEPTORS != null && !NETWORK_INTERCEPTORS.isEmpty()){
                for (Interceptor interceptor : NETWORK_INTERCEPTORS) {
                    BUILDER.addNetworkInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptors()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    private static final class RestServiceHolder {
        private static final RestService REST_SERVICE = RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
}
