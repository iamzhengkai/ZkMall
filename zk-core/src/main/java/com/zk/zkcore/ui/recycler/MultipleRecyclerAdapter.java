package com.zk.zkcore.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zk.zkcore.R;
import com.zk.zkcore.banner.GlideImageLoader;
import com.zk.zkcore.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/15.
 */

public class MultipleRecyclerAdapter
        extends BaseMultiItemQuickAdapter<MultipleItemEntity, MultipleViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, OnBannerListener {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    //确保只初始化一次banner，防止banner重复加载
    private boolean isBannerInited = false;

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    protected void convert(MultipleViewHolder helper, final MultipleItemEntity item) {
        String text = null;
        String imageUrl = null;
        ArrayList<String> bannerImages = null;
        ImageView imageView = null;
      /*  GridLayoutManager layoutManager = (GridLayoutManager)getRecyclerView().getLayoutManager();
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return item.getField(MultipleFields.SPAN_SIZE);
            }
        });*/
        switch (helper.getItemViewType()) {
            case ItemType.TEXT:
                text = item.getField(MultipleFields.TEXT);
                helper.setText(R.id.item_text_single, text);
                break;
            case ItemType.IMAGE:
                imageUrl = item.getField(MultipleFields.IMAGE_URL);
                imageView = helper.getView(R.id.item_image_single);
                imageView.setImageDrawable(null);
               /* Glide.with(mContext)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()
                        .centerCrop()
//                        .into((ImageView) helper.getView(R.id.item_image_single));
                        .into(imageView);*/
                Picasso.with(mContext)
                        .load(imageUrl)
                        .into(imageView);
                break;
            case ItemType.IMAGE_TEXT:
                text = item.getField(MultipleFields.TEXT);
                imageUrl = item.getField(MultipleFields.IMAGE_URL);
                helper.setText(R.id.item_text, text);
                imageView = helper.getView(R.id.item_image);
                imageView.setImageDrawable(null);
                /*Glide.with(mContext)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .dontAnimate()
                        .centerCrop()
//                        .into((ImageView) helper.getView(R.id.item_image));
                        .into(imageView);*/
                Picasso.with(mContext)
                        .load(imageUrl)
                        .into(imageView);
                break;
            case ItemType.BANNER:
                if (!isBannerInited) {
                    bannerImages = item.getField(MultipleFields.BANNERS);
                    Banner banner = helper.getView(R.id.item_banner);
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                            .setIndicatorGravity(BannerConfig.RIGHT)
                            .setImageLoader(new GlideImageLoader())
                            .setImages(bannerImages)
                            .setOnBannerListener(this)
                            .start();
                    isBannerInited = true;
                }

                break;
        }
    }

    private void init() {
        //设置不同的item布局
        addItemType(ItemType.TEXT, R.layout.item_multipart_text);
        addItemType(ItemType.IMAGE, R.layout.item_multipart_image);
        addItemType(ItemType.IMAGE_TEXT, R.layout.item_multipart_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multipart_banner);
        //设置条目宽度占比监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data) {
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConvertor convertor) {
        return new MultipleRecyclerAdapter(convertor.convert());
    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtils.showShortToast("点击了 " + position);
    }

    @Override
    public void onViewRecycled(MultipleViewHolder holder) {
        super.onViewRecycled(holder);
        ImageView imageView = null;
        switch (holder.getItemViewType()) {
            case ItemType.IMAGE:
                imageView = holder.getView(R.id.item_image_single);
//                Glide.clear(imageView);
                imageView.setImageDrawable(null);
                break;
            case ItemType.IMAGE_TEXT:
                imageView = holder.getView(R.id.item_image);
//                Glide.clear(imageView);
                imageView.setImageDrawable(null);
                break;
        }
    }
}
