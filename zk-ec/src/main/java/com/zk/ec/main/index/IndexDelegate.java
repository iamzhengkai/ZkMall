package com.zk.ec.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.youth.banner.util.DensityUtil;
import com.zk.ec.R;
import com.zk.ec.R2;
import com.zk.zkcore.delegates.bottom.BottomItemDelegate;
import com.zk.zkcore.refresh.RefreshHandler;
import com.zk.zkcore.ui.recycler.BaseDecoration;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/13.
 */

public class IndexDelegate extends BottomItemDelegate {
    @BindView(R2.id.rv_index)
    RecyclerView mRvIndex;
    @BindView(R2.id.srl_index)
    SwipeRefreshLayout mSrlIndex;
    @BindView(R2.id.icon_index_scan)
    IconTextView mIconIndexScan;
    @BindView(R2.id.et_search_view)
    AppCompatEditText mEtSearchView;
    @BindView(R2.id.icon_index_message)
    IconTextView mIconIndexMessage;
    @BindView(R2.id.tb_index)
    Toolbar mTbIndex;


    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecyclerView();
        RefreshHandler refreshHandler = RefreshHandler.create(mSrlIndex,mRvIndex,new IndexDataConvertor());
        refreshHandler.firstPage("https://api.bmob.cn/1/classes/tab_index_data");
//        mRefreshHandler.firstPage("https://api.bmob.cn/1/classes/tab_index_data");
      /*  RestClient.builder()
                .url("https://api.bmob.cn/1/classes/tab_index_data")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mSrlIndex.setRefreshing(false);
                        ToastUtils.showShortToast(response);
                        LoggerCompat.json(response);
                        IndexDataConvertor convertor = new IndexDataConvertor(response);
                        ArrayList<MultipleItemEntity> convert = convertor.convert();
                        LoggerCompat.d(convert.get(0).getFields());

                        MultipleRecyclerAdapter adapter = MultipleRecyclerAdapter.create(convert);
                        mRvIndex.setLayoutManager(new GridLayoutManager(getContext(),4));
                        mRvIndex.setAdapter(adapter);


                    }
                })
                .build()
                .get();*/
    }

    private void initRecyclerView(){
        final GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        mRvIndex.setLayoutManager(manager);
        mRvIndex.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(_mActivity,R.color.wechat_gray),4));
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
//        mRefreshHandler.firstPage("https://api.bmob.cn/1/classes/tab_index_data");
    }

    private void initRefreshLayout(){
        //设置刷新箭头颜色
        mSrlIndex.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        //设置触发刷新位置和涮新图标显示位置
        mSrlIndex.setProgressViewOffset(true, DensityUtil.dp2px(getContext(),80),DensityUtil.dp2px(getContext(),100));

    }



}
