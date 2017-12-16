package com.zk.zkmall.generators;

import com.zk.zk_annotations.annotations.AppRegisterGenerator;
import com.zk.zkcore.wechat.templates.AppRegisterTemplate;

/**
 * Created by Administrator on 2017/12/12.
 */
@AppRegisterGenerator(packageName = "com.zk.zkmall",
        registerTemplate = AppRegisterTemplate.class)
public interface AppRegister {
}
