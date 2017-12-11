package com.zk.ec.sign;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.zk.ec.R;
import com.zk.ec.R2;
import com.zk.ec.bean.ResponseError;
import com.zk.ec.bean.User;
import com.zk.zkcore.delegates.CoreDelegate;
import com.zk.zkcore.net.RestClient;
import com.zk.zkcore.net.callback.IError;
import com.zk.zkcore.net.callback.IFailure;
import com.zk.zkcore.net.callback.ISuccess;
import com.zk.zkcore.util.ToastUtils;
import com.zk.zkcore.util.log.LoggerCompat;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/10.
 */

public class SignUpDelegate extends CoreDelegate {

    @BindView(R2.id.et_sign_up_name)
    TextInputEditText mEtSignUpName;
    @BindView(R2.id.et_sign_up_email)
    TextInputEditText mEtSignUpEmail;
    @BindView(R2.id.et_sign_up_phone)
    TextInputEditText mEtSignUpPhone;
    @BindView(R2.id.et_sign_up_password)
    TextInputEditText mEtSignUpPassword;
    @BindView(R2.id.et_sign_up_re_password)
    TextInputEditText mEtSignUpRePassword;
    @BindView(R2.id.bt_sign_up)
    AppCompatButton mBtSignUp;
    @BindView(R2.id.tv_link_sign_in)
    AppCompatTextView mTvLinkSignIn;
    private String mName;
    private String mEmail;
    private String mPhone;
    private String mPassword;
    private String mRepassword;
    private ISignListener mISignListener;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ISignListener){
            mISignListener = (ISignListener)activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    private boolean checkForm() {

        mName = mEtSignUpName.getText().toString();
        mEmail = mEtSignUpEmail.getText().toString();
        mPhone = mEtSignUpPhone.getText().toString();
        mPassword = mEtSignUpPassword.getText().toString();
        mRepassword = mEtSignUpRePassword.getText().toString();

        boolean isPass = true;
        if (TextUtils.isEmpty(mName)) {
            mEtSignUpName.setError("请输入姓名.");
            isPass = false;
        } else {
            mEtSignUpName.setError(null);
//            clearError(mEtSignUpName);
        }

        if (TextUtils.isEmpty(mEmail) || !Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
            mEtSignUpEmail.setError("请输入正确的邮箱.");
            isPass = false;
        } else {
            mEtSignUpEmail.setError(null);
//            clearError(mEtSignUpEmail);
        }

        if (TextUtils.isEmpty(mPhone) || !Patterns.PHONE.matcher(mPhone).matches()) {
            mEtSignUpPhone.setError("请输入正确的手机号码.");
            isPass = false;
        } else {
            mEtSignUpPhone.setError(null);
//            clearError(mEtSignUpPhone);
        }

        if (TextUtils.isEmpty(mPassword) || mPassword.length() < 6) {
            mEtSignUpPassword.setError("请输入不小于6位的密码.");
            isPass = false;
        } else {
            mEtSignUpPassword.setError(null);
//            clearError(mEtSignUpPassword);
        }

        if (TextUtils.isEmpty(mRepassword) || mPassword.length() < 6 || !mPassword.equals(mRepassword)) {
            mEtSignUpRePassword.setError("两次输入的密码不同,请检查后重新输入.");
            isPass = false;
        } else {
            mEtSignUpRePassword.setError(null);
//            clearError(mEtSignUpRePassword);
        }

        return isPass;
    }

    @OnClick(R2.id.tv_link_sign_in)
    public void onClickLink(){
        startWithPop(new SigninDelegate());
    }

    private static final String TAG = "SignUpDelegate";
    @OnClick(R2.id.bt_sign_up)
    public void onClickSignup() {
        if (checkForm()) {
            final User user = new User(mName,mPassword,mPhone,mEmail);
            RestClient.builder()
                    .url("https://api.bmob.cn/1/users")
                    .header("Content-Type","application/json")
                    .raw(JSON.toJSONString(user))
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                final String objectId = jsonObject.getString("objectId");
                                if (!TextUtils.isEmpty(objectId)){
                                    user.setObjectId(objectId);
                                }
                                //save user info
                                SignHandler.onSignUpSuccess(user,mISignListener);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
                            SignHandler.onSignUpError(errorCode,msg,errorBody);
                        }
                    })
                    .build()
                    .post();
        }

    }
}
