package com.zk.zkmall.generators;

import com.zk.zk_annotations.annotations.PayEntryGenerator;
import com.zk.zkcore.wechat.templates.WXPayEntryTemplate;

/**
 * Created by Administrator on 2017/12/12.
 */
@PayEntryGenerator(packageName = "com.zk.zkmall",
        payEntryTemplete = WXPayEntryTemplate.class)
public interface WeChatPayEntry {
}
