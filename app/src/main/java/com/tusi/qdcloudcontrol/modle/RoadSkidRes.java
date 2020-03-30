package com.tusi.qdcloudcontrol.modle;

import java.util.List;

/**
 * Created by linfeng on 2018/10/24  17:34
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class RoadSkidRes {

    /**
     * road : {}
     * targetCell : [{"cellid":10000,"circle":{"center":{"altitude":20.973882,"vdop_accuracy":0.9,"latitude":31.33209647,"pdop_accuracy":99.8,"hdop_accuracy":98.3,"longitude":121.22972254},"radius":30}},null,null,null,null]
     * position : {"altitude":20.973882,"vdop_accuracy":0.9,"latitude":31.33209647,"pdop_accuracy":99.8,"hdop_accuracy":98.3,"longitude":121.22972254}
     * eventType : 10
     * uniqueId : 36b0593a16e54755bd27dca87ce1f33f
     * effectedLanes : [3]
     * timestamp : 1540297954804
     */

    private RoadBean road;
    private PositionBean position;
    private int eventType;
    private String uniqueId;
    private long timestamp;
    private List<TargetCellBean> targetCell;
    private List<Integer> effectedLanes;

    public RoadBean getRoad() {
        return road;
    }

    public void setRoad(RoadBean road) {
        this.road = road;
    }

    public PositionBean getPosition() {
        return position;
    }

    public void setPosition(PositionBean position) {
        this.position = position;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<TargetCellBean> getTargetCell() {
        return targetCell;
    }

    public void setTargetCell(List<TargetCellBean> targetCell) {
        this.targetCell = targetCell;
    }

    public List<Integer> getEffectedLanes() {
        return effectedLanes;
    }

    public void setEffectedLanes(List<Integer> effectedLanes) {
        this.effectedLanes = effectedLanes;
    }

    public static class RoadBean {
    }

    public static class PositionBean {
        /**
         * altitude : 20.973882
         * vdop_accuracy : 0.9
         * latitude : 31.33209647
         * pdop_accuracy : 99.8
         * hdop_accuracy : 98.3
         * longitude : 121.22972254
         */

        private double altitude;
        private double vdop_accuracy;
        private double latitude;
        private double pdop_accuracy;
        private double hdop_accuracy;
        private double longitude;

        public double getAltitude() {
            return altitude;
        }

        public void setAltitude(double altitude) {
            this.altitude = altitude;
        }

        public double getVdop_accuracy() {
            return vdop_accuracy;
        }

        public void setVdop_accuracy(double vdop_accuracy) {
            this.vdop_accuracy = vdop_accuracy;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getPdop_accuracy() {
            return pdop_accuracy;
        }

        public void setPdop_accuracy(double pdop_accuracy) {
            this.pdop_accuracy = pdop_accuracy;
        }

        public double getHdop_accuracy() {
            return hdop_accuracy;
        }

        public void setHdop_accuracy(double hdop_accuracy) {
            this.hdop_accuracy = hdop_accuracy;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    public static class TargetCellBean {
        /**
         * cellid : 10000
         * circle : {"center":{"altitude":20.973882,"vdop_accuracy":0.9,"latitude":31.33209647,"pdop_accuracy":99.8,"hdop_accuracy":98.3,"longitude":121.22972254},"radius":30}
         */

        private int cellid;
        private CircleBean circle;

        public int getCellid() {
            return cellid;
        }

        public void setCellid(int cellid) {
            this.cellid = cellid;
        }

        public CircleBean getCircle() {
            return circle;
        }

        public void setCircle(CircleBean circle) {
            this.circle = circle;
        }

        public static class CircleBean {
            /**
             * center : {"altitude":20.973882,"vdop_accuracy":0.9,"latitude":31.33209647,"pdop_accuracy":99.8,"hdop_accuracy":98.3,"longitude":121.22972254}
             * radius : 30
             */

            private CenterBean center;
            private int radius;

            public CenterBean getCenter() {
                return center;
            }

            public void setCenter(CenterBean center) {
                this.center = center;
            }

            public int getRadius() {
                return radius;
            }

            public void setRadius(int radius) {
                this.radius = radius;
            }

            public static class CenterBean {
                /**
                 * altitude : 20.973882
                 * vdop_accuracy : 0.9
                 * latitude : 31.33209647
                 * pdop_accuracy : 99.8
                 * hdop_accuracy : 98.3
                 * longitude : 121.22972254
                 */

                private double altitude;
                private double vdop_accuracy;
                private double latitude;
                private double pdop_accuracy;
                private double hdop_accuracy;
                private double longitude;

                public double getAltitude() {
                    return altitude;
                }

                public void setAltitude(double altitude) {
                    this.altitude = altitude;
                }

                public double getVdop_accuracy() {
                    return vdop_accuracy;
                }

                public void setVdop_accuracy(double vdop_accuracy) {
                    this.vdop_accuracy = vdop_accuracy;
                }

                public double getLatitude() {
                    return latitude;
                }

                public void setLatitude(double latitude) {
                    this.latitude = latitude;
                }

                public double getPdop_accuracy() {
                    return pdop_accuracy;
                }

                public void setPdop_accuracy(double pdop_accuracy) {
                    this.pdop_accuracy = pdop_accuracy;
                }

                public double getHdop_accuracy() {
                    return hdop_accuracy;
                }

                public void setHdop_accuracy(double hdop_accuracy) {
                    this.hdop_accuracy = hdop_accuracy;
                }

                public double getLongitude() {
                    return longitude;
                }

                public void setLongitude(double longitude) {
                    this.longitude = longitude;
                }
            }
        }
    }
}
