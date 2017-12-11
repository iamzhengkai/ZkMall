package com.zk.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;
import com.zk.ec.R;
import com.zk.ec.R2;
import com.zk.ec.bean.User;
import com.zk.zkcore.delegates.CoreDelegate;
import com.zk.zkcore.net.RestClient;
import com.zk.zkcore.net.callback.IError;
import com.zk.zkcore.net.callback.IFailure;
import com.zk.zkcore.net.callback.ISuccess;
import com.zk.zkcore.util.ToastUtils;
import com.zk.zkcore.util.log.LoggerCompat;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/10.
 */

public class SigninDelegate extends CoreDelegate {
    @BindView(R2.id.et_sign_in_email)
    TextInputEditText mEtEmail;
    @BindView(R2.id.et_sign_in_password)
    TextInputEditText mEtPassword;
    @BindView(R2.id.bt_sign_in)
    AppCompatButton mBtSignIn;
    @BindView(R2.id.tv_link_sign_up)
    AppCompatTextView mTvLinkSignUp;
    @BindView(R2.id.icon_sign_in_wechat)
    IconTextView mIconSignInWechat;
    private ISignListener mISignListener;

    private String mEmail;
    private String mPassword;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener) {
            mISignListener = (ISignListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    private boolean checkForm() {

        mEmail = mEtEmail.getText().toString();
        mPassword = mEtPassword.getText().toString();

        boolean isPass = true;
        if (TextUtils.isEmpty(mEmail)) {
            mEtPassword.setError("请输入正确的邮箱/手机号/用户名.");
            isPass = false;
        } else {
            mEtPassword.setError(null);
//            clearError(mEtSignUpEmail);
        }

        if (TextUtils.isEmpty(mPassword) || mPassword.length() < 6) {
            mEtPassword.setError("请输入不小于6位的密码.");
            isPass = false;
        } else {
            mEtPassword.setError(null);
//            clearError(mEtSignUpPassword);
        }

        return isPass;
    }

    @OnClick(R2.id.bt_sign_in)
    public void onClickSignin() {
        if (checkForm()) {
            //TODO 处理登陆逻辑
            if (checkForm()) {
                /*final User user = new User(mEmail, mPassword, null, null);
                LoggerCompat.d(JSON.toJSONString(user));*/
                RestClient.builder()
                        .url("https://api.bmob.cn/1/login")
                        .header("Content-Type", "application/json")
                        .param("username",mEmail)
                        .param("password",mPassword)
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String response) {
                                SignHandler.onSignInSuccess(mISignListener);
                            }
                        })
                        .failure(new IFailure() {
                            @Override
                            public void onFaiure(Throwable throwable) {
                                ToastUtils.showLongToast("网络错误: " + throwable.getMessage());
                            }
                        })
                        .error(new IError() {
                            @Override
                            public void onError(int errorCode, String msg, String errorBody) {
                                SignHandler.onSignInError(errorCode, msg, errorBody);
                            }
                        })
                        .build()
                        .get();
            }
        }
    }

    @OnClick(R2.id.icon_sign_in_wechat)
    public void onClickWechat() {

    }

    @OnClick(R2.id.tv_link_sign_up)
    public void onClickLink() {
//        start(new SignUpDelegate(),SINGLETASK);
        startWithPop(new SignUpDelegate());
    }
}
