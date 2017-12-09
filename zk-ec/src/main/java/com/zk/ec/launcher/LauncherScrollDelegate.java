package com.zk.ec.launcher;

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
import com.zk.zkcore.banner.GlideImageLoader;
import com.zk.zkcore.delegates.CoreDelegate;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/12/9.
 */

public class LauncherScrollDelegate extends CoreDelegate {
    private static final String TAG = "LauncherScrollDelegate";
    @BindView(R2.id.banner)
    Banner mBanner;
    @BindView(R2.id.bt_start)
    AppCompatButton mButton;

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
                .setBannerStyle(BannerConfig.SPLASH_CIRCLE_INDICATOR)
                .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (position == images.size()-1){
                            mButton.setVisibility(View.VISIBLE);
                        }else {
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
}
