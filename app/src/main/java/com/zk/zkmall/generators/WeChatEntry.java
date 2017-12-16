package com.zk.zkmall.generators;

import com.zk.zk_annotations.annotations.EntryGenerator;
import com.zk.zkcore.wechat.templates.WXEntryTemplate;

/**
 * Created by Administrator on 2017/12/12.
 */
@EntryGenerator(packageName = "com.zk.zkmall",
        entryTemplete = WXEntryTemplate.class)
public interface WeChatEntry {
}
