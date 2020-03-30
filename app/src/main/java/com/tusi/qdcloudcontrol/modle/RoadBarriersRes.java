package com.tusi.qdcloudcontrol.modle;

import java.util.List;

/**
 * Created by linfeng on 2018/10/17  15:55
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class RoadBarriersRes {


    /**
     * confidence : 1.0
     * targetCell : [{"cellid":10000,"circle":{"center":{"altitude":20.973882,"vdop_accuracy":0.9,"latitude":31.33209647,"pdop_accuracy":78,"hdop_accuracy":76.3,"longitude":121.22972254},"radius":30}},null,null,null,null]
     * size : {}
     * road : {}
     * position : {"altitude":20.973882,"vdop_accuracy":0.9,"latitude":31.33209647,"pdop_accuracy":78,"hdop_accuracy":76.3,"longitude":121.22972254}
     * uniqueId : 00222f32534a4b48913387e026de6165
     * direction : {}
     * effectedLanes : [null]
     * timestamp : 1540295822815
     */

    private double confidence;
    private SizeBean size;
    private RoadBean road;
    private PositionBean position;
    private String uniqueId;
    private DirectionBean direction;
    private long timestamp;
    private List<TargetCellBean> targetCell;

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public SizeBean getSize() {
        return size;
    }

    public void setSize(SizeBean size) {
        this.size = size;
    }

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

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public DirectionBean getDirection() {
        return direction;
    }

    public void setDirection(DirectionBean direction) {
        this.direction = direction;
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

    public static class SizeBean {
    }

    public static class RoadBean {
    }

    public static class PositionBean {
        /**
         * altitude : 20.973882
         * vdop_accuracy : 0.9
         * latitude : 31.33209647
         * pdop_accuracy : 78.0
         * hdop_accuracy : 76.3
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

    public static class DirectionBean {
    }

    public static class TargetCellBean {
        /**
         * cellid : 10000
         * circle : {"center":{"altitude":20.973882,"vdop_accuracy":0.9,"latitude":31.33209647,"pdop_accuracy":78,"hdop_accuracy":76.3,"longitude":121.22972254},"radius":30}
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
             * center : {"altitude":20.973882,"vdop_accuracy":0.9,"latitude":31.33209647,"pdop_accuracy":78,"hdop_accuracy":76.3,"longitude":121.22972254}
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
                 * pdop_accuracy : 78.0
                 * hdop_accuracy : 76.3
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
