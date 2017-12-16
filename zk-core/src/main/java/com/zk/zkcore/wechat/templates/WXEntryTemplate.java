package com.zk.zkcore.wechat.templates;

import com.zk.zkcore.wechat.BaseWxEntryActivity;
import com.zk.zkcore.wechat.WeChat;
import com.zk.zkcore.wechat.callbacks.IWeChatSignInCallback;

/**
 * 微信登陆返回的实际Activity是我们自定义的annotation processor(基于该WXEntryTemplate)生成的那个Activity
 * 而实际上我们往往需要根据业务需要指定自己的Activity，所以要在成功回调中根据需求进行自己的处理
 * Created by Administrator on 2017/12/12.
 */
public class WXEntryTemplate extends BaseWxEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        //直接finish掉
        finish();
        //去掉动画效果，实现对用户的隐形
        overridePendingTransition(0,0);
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        IWeChatSignInCallback signInCallback = WeChat.getInstance().getSignInCallback();
        if (signInCallback != null){
            signInCallback.onSignInSuccess(userInfo);
        }
    }
}
