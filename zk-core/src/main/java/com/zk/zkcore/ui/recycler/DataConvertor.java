package com.zk.zkcore.ui.recycler;

import android.text.TextUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/14.
 */

public abstract class DataConvertor {
    protected final ArrayList<MultipleItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData;
    public abstract ArrayList<MultipleItemEntity> convert();

    public DataConvertor setJsonData(String json){
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData(){
        if (TextUtils.isEmpty(mJsonData)){
            throw new RuntimeException("DATA is empty");
        }
        return mJsonData;
    }
}
