package com.zk.zkcore.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2017/12/14.
 */

public class MultipleItemEntity implements MultiItemEntity{
    private final ReferenceQueue<LinkedHashMap<Object,Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object,Object> MULTIPULE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object,Object>> FIELDS_REFERENCE
            = new SoftReference<>(MULTIPULE_FIELDS,ITEM_QUEUE);

    public MultipleItemEntity(LinkedHashMap<Object,Object> fields){
        FIELDS_REFERENCE.get().putAll(fields);
    }
    @Override
    public int getItemType() {
        return (int) FIELDS_REFERENCE.get().get(MultipleFields.ITEM_TYPE);
    }

    public final <T> T getField(Object key){
        return (T)FIELDS_REFERENCE.get().get(key);
    }

    public final LinkedHashMap<?,?> getFields(){
        return FIELDS_REFERENCE.get();
    }

    public final MultipleItemEntity setField(Object key,Object value){
        FIELDS_REFERENCE.get().put(key,value);
        return this;
    }

    public static Builder builder(){
        return new Builder();
    }


    public static class Builder{
        private static final LinkedHashMap<Object,Object> FIELDS = new LinkedHashMap<>();

        public Builder(){
            FIELDS.clear();
        }

        public final Builder setItemType(int itemType){
            FIELDS.put(MultipleFields.ITEM_TYPE,itemType);
            return this;
        }

        public final Builder setField(Object key,Object value){
            FIELDS.put(key,value);
            return this;
        }

        public final Builder setFields(LinkedHashMap<?,?> fields){
            FIELDS.putAll(fields);
            return this;
        }

        public final MultipleItemEntity build(){
            return new MultipleItemEntity(FIELDS);
        }

    }
}
