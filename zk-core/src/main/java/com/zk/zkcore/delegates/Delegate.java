package com.zk.zkcore.delegates;

/**
 * Created by Administrator on 2017/12/8.
 */

public abstract class Delegate extends PermissionCheckDelegate {
    public <T extends Delegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
}
