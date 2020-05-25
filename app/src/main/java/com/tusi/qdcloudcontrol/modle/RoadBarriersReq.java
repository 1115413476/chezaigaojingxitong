package com.tusi.qdcloudcontrol.modle;

import java.util.List;

/**
 * Created by linfeng on 2018/10/17  18:51
 * Email zhanglinfengdev@163.com
 * Function details...
 *
 */
public class RoadBarriersReq {

    /**
     * vehicleId : 998887700
     * obstacleType : 0
     * geoInfo : {"vendor":"测试障碍物感知上报","position":{"longitude":116.12334322,"latitude":39.23222234,"altitude":20.973882,"pdop_accuracy":78,"hdop_accuracy":76.3,"vdop_accuracy":0.9},"roadId":78,"affectedLanes":[{"positiveLanes":343,"negativeLanes":213}]}
     * size : {"width":45.2,"depth":23.1,"height":33.2}
     * color : 24
     * movable : false
     * direction : {"e":67.23,"n":45.55,"u":23.56}
     * velocity : 0.33
     * dangerLevel : 1
     * timestamp : 1539323232000
     */

    private int vehicleId;
    private int obstacleType;
    private GeoInfoBean geoInfo;
    private SizeBean size;
    private int color;
    private boolean movable;
    private DirectionBean direction;
    private double velocity;
    private int dangerLevel;
    private long timestamp;

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getObstacleType() {
        return obstacleType;
    }

    public void setObstacleType(int obstacleType) {
        this.obstacleType = obstacleType;
    }

    public GeoInfoBean getGeoInfo() {
        return geoInfo;
    }

    public void setGeoInfo(GeoInfoBean geoInfo) {
        this.geoInfo = geoInfo;
    }

    public SizeBean getSize() {
        return size;
    }

    public void setSize(SizeBean size) {
        this.size = size;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public DirectionBean getDirection() {
        return direction;
    }

    public void setDirection(DirectionBean direction) {
        this.direction = direction;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public int getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(int dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class GeoInfoBean {
        /**
         * vendor : 测试障碍物感知上报
         * position : {"longitude":116.12334322,"latitude":39.23222234,"altitude":20.973882,"pdop_accuracy":78,"hdop_accuracy":76.3,"vdop_accuracy":0.9}
         * roadId : 78
         * affectedLanes : [{"positiveLanes":343,"negativeLanes":213}]
         */

        private String vendor;
        private PositionBean position;
        private int roadId;
        private List<AffectedLanesBean> affectedLanes;

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public PositionBean getPosition() {
            return position;
        }

        public void setPosition(PositionBean position) {
            this.position = position;
        }

        public int getRoadId() {
            return roadId;
        }

        public void setRoadId(int roadId) {
            this.roadId = roadId;
        }

        public List<AffectedLanesBean> getAffectedLanes() {
            return affectedLanes;
        }

        public void setAffectedLanes(List<AffectedLanesBean> affectedLanes) {
            this.affectedLanes = affectedLanes;
        }

        public static class PositionBean {
            /**
             * longitude : 116.12334322
             * latitude : 39.23222234
             * altitude : 20.973882
             * pdop_accuracy : 78
             * hdop_accuracy : 76.3
             * vdop_accuracy : 0.9
             */

            private double longitude;
            private double latitude;
            private double altitude;
            private int pdop_accuracy;
            private double hdop_accuracy;
            private double vdop_accuracy;

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

            public int getPdop_accuracy() {
                return pdop_accuracy;
            }

            public void setPdop_accuracy(int pdop_accuracy) {
                this.pdop_accuracy = pdop_accuracy;
            }

            public double getHdop_accuracy() {
                return hdop_accuracy;
            }

            public void setHdop_accuracy(double hdop_accuracy) {
                this.hdop_accuracy = hdop_accuracy;
            }

            public double getVdop_accuracy() {
                return vdop_accuracy;
            }

            public void setVdop_accuracy(double vdop_accuracy) {
                this.vdop_accuracy = vdop_accuracy;
            }
        }

        public static class AffectedLanesBean {
            /**
             * positiveLanes : 343
             * negativeLanes : 213
             */

            private int positiveLanes;
            private int negativeLanes;

            public int getPositiveLanes() {
                return positiveLanes;
            }

            public void setPositiveLanes(int positiveLanes) {
                this.positiveLanes = positiveLanes;
            }

            public int getNegativeLanes() {
                return negativeLanes;
            }

            public void setNegativeLanes(int negativeLanes) {
                this.negativeLanes = negativeLanes;
            }
        }
    }

    public static class SizeBean {
        /**
         * width : 45.2
         * depth : 23.1
         * height : 33.2
         */

        private double width;
        private double depth;
        private double height;

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getDepth() {
            return depth;
        }

        public void setDepth(double depth) {
            this.depth = depth;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }
    }

    public static class DirectionBean {
        /**
         * e : 67.23
         * n : 45.55
         * u : 23.56
         */

        private double e;
        private double n;
        private double u;

        public double getE() {
            return e;
        }

        public void setE(double e) {
            this.e = e;
        }

        public double getN() {
            return n;
        }

        public void setN(double n) {
            this.n = n;
        }

        public double getU() {
            return u;
        }

        public void setU(double u) {
            this.u = u;
        }
    }
}
