package com.zk.zkmall;

import com.zk.zkcore.activities.ProxyActivity;
import com.zk.zkcore.delegates.MallDelegate;
import com.zk.zkcore.net.RestClient;
import com.zk.zkcore.net.RestClientBuilder;
import com.zk.zkcore.net.callback.IError;
import com.zk.zkcore.net.callback.IFailure;
import com.zk.zkcore.net.callback.ISuccess;

public class MainActivity extends ProxyActivity {

    @Override
    public MallDelegate setRootDelegate() {
        return new TestDelegate();
    }


}
