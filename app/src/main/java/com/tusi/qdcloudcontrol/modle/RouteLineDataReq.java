package com.tusi.qdcloudcontrol.modle;

import com.baidu.mapapi.model.LatLng;

import java.util.List;

/**
 * Created by linfeng on 2018/10/22  10:56
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class RouteLineDataReq {
    String vehicleId;
    String orderId;
    public List<LatLng> data;

    public RouteLineDataReq(String vehicleId, String orderId, List<LatLng> latLngs) {
        this.vehicleId = vehicleId;
        this.orderId = orderId;
        this.data = latLngs;
    }

    public static class LatLng {
        public double lat;
        public double lon;

        public LatLng(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }
    }
}
