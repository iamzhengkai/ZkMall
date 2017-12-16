package com.zk.zkcore.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zk.zkcore.app.ConfigurationManager;
import com.zk.zkcore.net.RestClient;
import com.zk.zkcore.net.callback.ISuccess;
import com.zk.zkcore.ui.recycler.DataConvertor;
import com.zk.zkcore.ui.recycler.MultipleRecyclerAdapter;

/**
 * Created by Administrator on 2017/12/14.
 */

public class RefreshHandler implements
        SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener {
    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLERVIEW;
    private MultipleRecyclerAdapter mAdapter = null;
    private final DataConvertor CONVERTOR;

    private RefreshHandler(SwipeRefreshLayout refresh_layout,
                          RecyclerView recyclerview,
                          DataConvertor convertor,
                          PagingBean bean) {
        this.REFRESH_LAYOUT = refresh_layout;
        this.BEAN = bean;
        this.RECYCLERVIEW = recyclerview;
        this.CONVERTOR = convertor;
    }

    public static RefreshHandler create(SwipeRefreshLayout refresh_layout,
                                        RecyclerView recyclerview,
                                        DataConvertor convertor
                                        ){
        return new RefreshHandler(refresh_layout,recyclerview,convertor,new PagingBean());
    }

    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);
        ConfigurationManager.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    public void firstPage(String url){
        BEAN.setDelayed(1000);
        RestClient.builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject object = JSONObject.parseObject(response);
                        JSONObject data = object.getJSONArray("results").getJSONObject(0).getJSONObject("data");
                        BEAN.setTotal(data.getInteger("total"));
                        BEAN.setPageSize(data.getInteger("page_size"));
                        mAdapter = MultipleRecyclerAdapter.create(CONVERTOR.setJsonData(response));
                        /*BEAN.setCurrentCount(6);
                        BEAN.addIndex();*/
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLERVIEW);
                        RECYCLERVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
