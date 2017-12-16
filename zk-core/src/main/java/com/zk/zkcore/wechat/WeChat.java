package com.zk.zkcore.wechat;

import android.app.Activity;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zk.zkcore.app.ConfigType;
import com.zk.zkcore.app.ConfigurationManager;
import com.zk.zkcore.wechat.callbacks.IWeChatSignInCallback;

/**
 * 微信管理类
 * 包括：微信登陆，微信支付和相关回调等
 * 注意：微信的相关回调也是在该类进行配置的
 * Created by Administrator on 2017/12/12.
 */

public class WeChat {
    static final String APP_ID = ConfigurationManager.getConfiguration(ConfigType.WE_CHAT_APP_ID);
    static final String APP_SECRET = ConfigurationManager.getConfiguration(ConfigType.WE_CHAT_APP_SECRET);
    private final IWXAPI WXAPI;

    private IWeChatSignInCallback mIWeChatSignInCallback;

    private WeChat() {
        final Activity activity = ConfigurationManager.getConfiguration(ConfigType.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    private static final class Holder {
        private static final WeChat INSTANCE = new WeChat();
    }

    public static WeChat getInstance() {
        return Holder.INSTANCE;
    }



    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public final void signIn() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        //实际请求微信平台接口，请求结束后会回调固定的Activity，即：.wxapi.WXEntryActivity
        WXAPI.sendReq(req);
    }

    public WeChat onSignInSuccess(IWeChatSignInCallback IWeChatSignInCallback) {
        mIWeChatSignInCallback = IWeChatSignInCallback;
        return this;
    }
    public IWeChatSignInCallback getSignInCallback() {
        return mIWeChatSignInCallback;
    }


}
