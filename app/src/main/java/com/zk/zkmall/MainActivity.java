package com.zk.zkmall;

import com.zk.ec.launcher.LauncherScrollDelegate;
import com.zk.zkcore.activities.ProxyActivity;
import com.zk.zkcore.delegates.CoreDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    public CoreDelegate setRootDelegate() {
//        return new TestDelegate();
//        return new LauncherDelegate();
        return new LauncherScrollDelegate();
    }


}
