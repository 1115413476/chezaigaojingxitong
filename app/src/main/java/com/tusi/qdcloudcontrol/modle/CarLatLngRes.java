package com.tusi.qdcloudcontrol.modle;

/**
 * Created by linfeng on 2018/10/17  15:18
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class CarLatLngRes {


    /**
     * longitude : 0.0
     * latitude : 0.0
     * altitude : 0.0
     * direction : 0.0
     */

    private double longitude;
    private double latitude;
    private double altitude;
    private double direction;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }
}
