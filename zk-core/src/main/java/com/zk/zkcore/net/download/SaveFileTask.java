package com.zk.zkcore.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.zk.zkcore.app.ConfigurationManager;
import com.zk.zkcore.net.callback.IRequest;
import com.zk.zkcore.net.callback.ISuccess;
import com.zk.zkcore.util.FileUtil;
import com.zk.zkcore.util.FileUtils;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/12/9.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {

    private final IRequest REQUEST;
    private final ISuccess SUECCESS;

    public SaveFileTask(IRequest REQUEST, ISuccess SUECCESS) {
        this.REQUEST = REQUEST;
        this.SUECCESS = SUECCESS;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        String name = (String) params[3];

        final InputStream is = body.byteStream();
        if (TextUtils.isEmpty(downloadDir)){
            downloadDir = "download";
        }
        if (TextUtils.isEmpty(extension)){
            extension = "";
        }
        if (TextUtils.isEmpty(name)){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,downloadDir,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUECCESS != null){
            SUECCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null){
            REQUEST.onRequestEnd();
        }

        autoInstallApk(file);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    private void autoInstallApk(File file){
        if (file == null){
            return;
        }
        if (!file.exists()){
            return;
        }
        if (!FileUtil.getExtension(file).equals("apk")){
            return;
        }

        final Intent install = new Intent();
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setAction(Intent.ACTION_VIEW);
        String type = null;
        if (Build.VERSION.SDK_INT < 23) {
            type = "application/vnd.android.package-archive";
        } else {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(FileUtils.getFileExtension(file));
        }
        install.setDataAndType(Uri.fromFile(file),type);
        ConfigurationManager.getApplicationContext().startActivity(install);
    }
}
