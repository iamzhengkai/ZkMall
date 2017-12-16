package com.zk.zkcore.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wang.avi.AVLoadingIndicatorView;
import com.zk.zkcore.R;
import com.zk.zkcore.util.DimenUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/9.
 */

public class Loader {
    private static final int LOADER_SIZE_SCALE = 8;
    private static final int LOADER_OFFSET_SCALE = 10;
    private static final String DEFALUT_LOADER = LoaderStyle.BallClipRotatePulseIndicator.name();
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();

    public static void show(Context context, String type) {
        final AppCompatDialog dialog = new AppCompatDialog(context, R.style.loading_dialog);
        final AVLoadingIndicatorView loadingView = LoaderCreator.create(context, type);
        dialog.setContentView(loadingView);
        int deviceWidth = DimenUtil.getScreenWidth();
        int deviceHeight = DimenUtil.getScreenHeight();
        final Window dialogwindow = dialog.getWindow();
        if (dialogwindow != null) {
            WindowManager.LayoutParams layoutParams = dialogwindow.getAttributes();
            layoutParams.width = deviceWidth / LOADER_SIZE_SCALE;
            layoutParams.height = deviceHeight / LOADER_SIZE_SCALE;
            layoutParams.height = layoutParams.height + deviceHeight / LOADER_OFFSET_SCALE;
            layoutParams.gravity = Gravity.CENTER;
            dialogwindow.setAttributes(layoutParams);
        }
        LOADERS.add(dialog);
        dialog.show();
    }

    public static void show(Context context) {
        show(context, DEFALUT_LOADER);
    }

    public static void show(Context context,Enum<LoaderStyle> type){
        show(context,type.name());
    }

    public static void dismiss() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null){
                if (dialog.isShowing()){
//                    dialog.dismiss();
//                    使用cancel方法可以执行一些回调处理，而dismiss只能取消显示
                    dialog.cancel();
                }
            }
        }
    }
}
