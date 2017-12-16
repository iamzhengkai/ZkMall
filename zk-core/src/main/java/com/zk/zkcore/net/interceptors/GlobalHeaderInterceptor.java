package com.zk.zkcore.net.interceptors;

import android.text.TextUtils;

import com.zk.zkcore.app.ConfigType;
import com.zk.zkcore.app.ConfigurationManager;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/10.
 */

public class GlobalHeaderInterceptor extends BaseInterceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final String applicationId = ConfigurationManager.getConfiguration(ConfigType.BMOB_APPLICATION_ID);
        final String apiKey = ConfigurationManager.getConfiguration(ConfigType.BMOB_REST_API_KEY);

        if (!TextUtils.isEmpty(applicationId) && !TextUtils.isEmpty(apiKey)) {
            Request newRequest = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type","application/json")
                    .addHeader("X-Bmob-Application-Id", applicationId)
                    .addHeader("X-Bmob-REST-API-Key", apiKey)
                    .build();
            return chain.proceed(newRequest);
        }else {
            return chain.proceed(chain.request());
        }

    }
}
