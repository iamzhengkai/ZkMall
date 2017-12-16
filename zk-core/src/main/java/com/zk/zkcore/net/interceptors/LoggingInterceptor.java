package com.zk.zkcore.net.interceptors;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Function:
 *
 * @FileName: com.kevin.network_demo.api.LoggingInterceptor.java
 * @author: zk
 * @date: 2017-03-11 12:19
 */

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {

        Request request = chain.request();
        //KLog.d(String.format("Sending request %s on %s%n%s", request.url(),  chain.connection(), request.headers()));
        Logger.d("Sending request %s on %s%n%s",request.url(),  chain.connection(), request.headers());
//        Logger.d("Sending request %s | protocol:%s header:%s",request.url(),  chain.connection().protocol().toString(), request.headers().toString());
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        /*KLog.d(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));*/
        Logger.d("Received response for %s in %.1fms%n%s",response.request().url(), (t2 - t1) / 1e6d, response.headers());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        /*KLog.json(content);*/
        Logger.json(content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}