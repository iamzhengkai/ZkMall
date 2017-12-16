package com.zk.zkcore.wechat;

import com.alibaba.fastjson.JSONObject;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.zk.zkcore.net.RestClient;
import com.zk.zkcore.net.callback.IError;
import com.zk.zkcore.net.callback.IFailure;
import com.zk.zkcore.net.callback.ISuccess;
import com.zk.zkcore.util.log.LoggerCompat;

/**
 * 处理微信相关回调
 * Created by Administrator on 2017/12/13.
 */

public abstract class BaseWxEntryActivity extends BaseWXActivity{
    //微信发送请求到第三方应用后的回调
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //第三方应用发送请求到微信后的回调
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp)baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(WeChat.APP_ID)
                .append("&secret=")
                .append(WeChat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");
        LoggerCompat.d("authUrl","authUrl = " + authUrl.toString());
        getAuth(authUrl.toString());
    }

    private void getAuth(final String authUrl){
        //第零步，发送登陆请求到微信平台
        //通过IWXAPI的sendReq方法向微信平台发送登陆请求
        //实际调用方法：WeChat.getInstance().signIn();
        //第一步，获取openid
        RestClient.builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject authObj = JSONObject.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openid");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl.append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");
                        LoggerCompat.d("userInfoUrl","userInfoUrl = " + userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    private void getUserInfo(String userInfoUrl) {
        //第二步，实际获取用户信息
        RestClient.builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onSignInSuccess(response);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure(Throwable throwable) {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int errorCode, String msg, String errorBody) {

                    }
                })
                .build()
                .get();
    }

    protected abstract void onSignInSuccess(String userInfo);
}
