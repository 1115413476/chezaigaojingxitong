package com.tusi.qdcloudcontrol.modle;

/**
 * Created by linfeng on 2018/10/22  20:05
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class QDHostModule {

//     tbox : 192.168.1.99:1883
//     cloud : 192.168.1.99:1883

    private String tbox;
    private String cloud;

    public String getTbox() {
        return tbox;
    }

    public void setTbox(String tbox) {
        this.tbox = tbox;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }
//    tbox 192.168.1.99:1883
//    云端：tcp://10.0.1.25:1883


    @Override
    public String toString() {
        return "QDHostModule{" +
                "tbox='" + tbox + '\'' +
                ", cloud='" + cloud + '\'' +
                '}';
    }
}
