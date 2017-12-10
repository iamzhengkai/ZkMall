package com.zk.ec.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.joanzapata.iconify.widget.IconTextView;
import com.zk.ec.R;
import com.zk.ec.R2;
import com.zk.zkcore.delegates.CoreDelegate;

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

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    private boolean checkForm() {

        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        boolean isPass = true;
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEtPassword.setError("请输入正确的邮箱.");
            isPass = false;
        } else {
            mEtPassword.setError(null);
//            clearError(mEtSignUpEmail);
        }


        if (TextUtils.isEmpty(password) || password.length() < 6) {
            mEtPassword.setError("请输入不小于6位的密码.");
            isPass = false;
        } else {
            mEtPassword.setError(null);
//            clearError(mEtSignUpPassword);
        }

        return isPass;
    }

    @OnClick(R2.id.bt_sign_in)
    public void onClickSignin(){
        if (checkForm()){
            //TODO 处理登陆逻辑

        }
    }
    @OnClick(R2.id.icon_sign_in_wechat)
    public void onClickWechat(){

    }

    @OnClick(R2.id.tv_link_sign_up)
    public void onClickLink(){
//        start(new SignUpDelegate(),SINGLETASK);
        startWithPop(new SignUpDelegate());
    }
}
