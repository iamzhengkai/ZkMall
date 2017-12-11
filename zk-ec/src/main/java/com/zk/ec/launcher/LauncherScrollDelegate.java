package com.zk.ec.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.zk.ec.R;
import com.zk.ec.R2;
import com.zk.zkcore.app.AccountManager;
import com.zk.zkcore.app.IUserChecker;
import com.zk.zkcore.banner.GlideImageLoader;
import com.zk.zkcore.delegates.CoreDelegate;
import com.zk.zkcore.ui.launcher.ILauncherListener;
import com.zk.zkcore.ui.launcher.OnLauncherFinishTag;
import com.zk.zkcore.util.SPUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/12/9.
 */

public class LauncherScrollDelegate extends CoreDelegate {
    private static final String TAG = "LauncherScrollDelegate";
    @BindView(R2.id.banner)
    Banner mBanner;
    @BindView(R2.id.bt_start)
    AppCompatButton mButton;
    private ILauncherListener mILauncherListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_scroll_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.launcher_01);
        images.add(R.drawable.launcher_02);
        images.add(R.drawable.launcher_03);
        images.add(R.drawable.launcher_04);
        images.add(R.drawable.launcher_05);
        mBanner.setImageLoader(new GlideImageLoader())
                .setImages(images)
                .disableLoop()
                .setOffscreenPageLimit(images.size())
                .setBannerStyle(BannerConfig.SPLASH_CIRCLE_INDICATOR)
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (position == images.size() - 1) {
                            mButton.setVisibility(View.VISIBLE);
                        } else {
                            mButton.setVisibility(View.GONE);
                        }
                        Log.d(TAG, "onPageSelected: position=" + position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
        mBanner.start();
    }

    @OnClick(R2.id.bt_start)
    public void onClick(View view) {
        SPUtils.setIsUsedFlag();
        toNext();
    }

    private void toNext() {
        //TODO 判断登陆状态，跳转到下一页面
        AccountManager.checkAccount(new IUserChecker() {//回调:细节未知,但是现在要用
            @Override
            public void onSignIn() {
                if (mILauncherListener != null){
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                }
            }

            @Override
            public void onNotSignIn() {
                if (mILauncherListener != null){
                    mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                }
            }
        });
    }
}
