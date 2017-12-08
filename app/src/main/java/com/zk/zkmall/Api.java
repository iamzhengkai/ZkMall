package com.zk.zkmall;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/12/8.
 */

public interface Api {
    @GET("/category")
    Call<String>  getCategory();

}
