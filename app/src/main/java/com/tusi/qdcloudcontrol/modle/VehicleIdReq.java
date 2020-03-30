package com.tusi.qdcloudcontrol.modle;

/**
 * Created by linfeng on 2018/10/17  14:57
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class VehicleIdReq {

    /**
     * yktype : vid
     */

    private String yktype;

    public String getYktype() {
        return yktype;
    }

    public void setYktype(String yktype) {
        this.yktype = yktype;
    }

    public VehicleIdReq() {
        this.yktype = "vid";
    }
}
