package com.tusi.qdcloudcontrol.modle;

/**
 * Created by linfeng on 2018/10/22  18:54
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class SignalGpsMode {
    public String startNodeId;
    public String roadId;
    public Double lat;
    public Double lng;

    private SignalGpsMode(String startNodeId, String raodId, Double lat, Double lng) {
        this.startNodeId = startNodeId;
        this.roadId = raodId;
        this.lat = lat;
        this.lng = lng;
    }

    public static SignalGpsMode parseInfo(String[] split) {
//        1-5-7,ROAD102,(121.22411621,31.32828932)
        final String stasrtNodeId = split[0];
        final String roadId = split[1];
        final Double lat = Double.parseDouble(split[3]);
        final Double lng = Double.parseDouble(split[2]);
        return new SignalGpsMode(stasrtNodeId, roadId, lat, lng);
    }
}
