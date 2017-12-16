package com.zk.ec.main.index;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zk.zkcore.ui.recycler.DataConvertor;
import com.zk.zkcore.ui.recycler.ItemType;
import com.zk.zkcore.ui.recycler.MultipleFields;
import com.zk.zkcore.ui.recycler.MultipleItemEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/14.
 */

public class IndexDataConvertor extends DataConvertor {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray results = JSONObject.parseObject(getJsonData()).getJSONArray("results");
        final JSONArray dataArray = results.getJSONObject(0).getJSONObject("data").getJSONArray("data");
        final int size = dataArray.size();
        JSONObject data;
        int goodsId;
        int spanSize;
        String imageUrl;
        String text;
        JSONArray banners;
        int type;
        ArrayList<String> bannerImages = new ArrayList<>();
        int bannerSize;
        MultipleItemEntity entity;

        for (int i = 0; i < size; i++) {
            data = dataArray.getJSONObject(i);
            goodsId = data.getInteger("goodsId");
            imageUrl = data.getString("imageUrl");
            text = data.getString("text");
            spanSize = data.getInteger("spanSize");
            banners = data.getJSONArray("banners");
            if (TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(text)){
                type = ItemType.TEXT;
            }else if (!TextUtils.isEmpty(imageUrl) && TextUtils.isEmpty(text)){
                type = ItemType.IMAGE;
            }else if (!TextUtils.isEmpty(imageUrl)&& !TextUtils.isEmpty(text)){
                type = ItemType.IMAGE_TEXT;
            }else {
                if (banners != null){
                    type = ItemType.BANNER;
                    //init banner
                    bannerSize = banners.size();
                    for (int j = 0; j < bannerSize; j++) {
                        bannerImages.add(banners.getString(j));
                    }
                }else {
                    throw new RuntimeException("未知的条目类型: " + data.toJSONString());
                }

            }

            entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ID,goodsId)
                    .setField(MultipleFields.ITEM_TYPE,type)
                    .setField(MultipleFields.SPAN_SIZE,spanSize)
                    .setField(MultipleFields.TEXT,text)
                    .setField(MultipleFields.IMAGE_URL,imageUrl)
                    .setField(MultipleFields.BANNERS,bannerImages)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
