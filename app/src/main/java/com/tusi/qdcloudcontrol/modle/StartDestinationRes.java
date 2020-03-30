package com.tusi.qdcloudcontrol.modle;

/**
 * 推送给pad的信息
 */
public class StartDestinationRes {

    /**
     * userId : 1
     * startLon : 121.22972254
     * startLat : 31.33209647
     * endLon : 121.22346433
     * endLat : 31.33010155
     * orderId : 6
     * status : 1
     */

    private int userId;
    private double startLon;
    private double startLat;
    private double endLon;
    private double endLat;
    private int orderId;
    private int status;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getStartLon() {
        return startLon;
    }

    public void setStartLon(double startLon) {
        this.startLon = startLon;
    }

    public double getStartLat() {
        return startLat;
    }

    public void setStartLat(double startLat) {
        this.startLat = startLat;
    }

    public double getEndLon() {
        return endLon;
    }

    public void setEndLon(double endLon) {
        this.endLon = endLon;
    }

    public double getEndLat() {
        return endLat;
    }

    public void setEndLat(double endLat) {
        this.endLat = endLat;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
