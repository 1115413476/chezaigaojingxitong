package com.tusi.qdcloudcontrol.utils;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by linfeng on 2018/10/15  16:12
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class CheckUtils {
    public static final double BAIDUNULLLATORLNG = 4.9E-324;

    public static boolean locationIsValid(LatLng latLng) {
        return !(latLng == null || latLng.latitude == BAIDUNULLLATORLNG || latLng.longitude == BAIDUNULLLATORLNG || latLng.latitude == 0.0 || latLng.longitude == 0.0);
    }
}
