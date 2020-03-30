package com.tusi.qdcloudcontrol.jni;

/**
 * Created by chenlipeng on 2018/10/20 0020.
 * describe :
 */

public class getroadinfo {

    static {
        System.loadLibrary("getroadinfo");
    }

    public native int java2c();

}
