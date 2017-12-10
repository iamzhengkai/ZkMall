package com.zk.zkcore.net.interceptors;

import android.text.TextUtils;

import com.zk.zkcore.app.ConfigType;
import com.zk.zkcore.app.Core;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/10.
 */

public class GlobalHearInterceptor extends BaseInterceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final String applicationId = Core.getConfiguration(ConfigType.BMOB_APPLICATION_ID);
        final String apiKey = Core.getConfiguration(ConfigType.BMOB_REST_API_KEY);

        if (!TextUtils.isEmpty(applicationId) && !TextUtils.isEmpty(apiKey)) {
            Request newRequest = chain.request()
                    .newBuilder()
                    .addHeader("X-Bmob-Application-Id", applicationId)
                    .addHeader("X-Bmob-REST-API-Key", apiKey)
                    .build();
            return chain.proceed(newRequest);
        }else {
            return chain.proceed(chain.request());
        }

    }
}
