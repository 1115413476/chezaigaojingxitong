package com.tusi.qdcloudcontrol.modle;

import java.util.List;

public class UpRoadIceIQ {


    /**
     * vehicleId : 998887700
     * eventType : 12
     * geoInfo : {"vendor":"dfjkdshfkj","position":{"longitude":116.376691,"latitude":39.973882,"altitude":20.973882,"pdop_accuracy":0.9,"hdop_accuracy":0.9,"vdop_accuracy":0.9},"roadid":34,"affectedLanes":[{"positiveLanes":343,"negativeLanes":213}]}
     * dangerLevel : 1
     * timestamp : 1528105852000
     */

    private int vehicleId;
    private int eventType;
    private GeoInfoBean geoInfo;
    private int dangerLevel;
    private long timestamp;

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public GeoInfoBean getGeoInfo() {
        return geoInfo;
    }

    public void setGeoInfo(GeoInfoBean geoInfo) {
        this.geoInfo = geoInfo;
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
         * vendor : dfjkdshfkj
         * position : {"longitude":116.376691,"latitude":39.973882,"altitude":20.973882,"pdop_accuracy":0.9,"hdop_accuracy":0.9,"vdop_accuracy":0.9}
         * roadid : 34
         * affectedLanes : [{"positiveLanes":343,"negativeLanes":213}]
         */

        private String vendor;
        private PositionBean position;
        private int roadid;
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

        public int getRoadid() {
            return roadid;
        }

        public void setRoadid(int roadid) {
            this.roadid = roadid;
        }

        public List<AffectedLanesBean> getAffectedLanes() {
            return affectedLanes;
        }

        public void setAffectedLanes(List<AffectedLanesBean> affectedLanes) {
            this.affectedLanes = affectedLanes;
        }

        public static class PositionBean {
            /**
             * longitude : 116.376691
             * latitude : 39.973882
             * altitude : 20.973882
             * pdop_accuracy : 0.9
             * hdop_accuracy : 0.9
             * vdop_accuracy : 0.9
             */

            private double longitude;
            private double latitude;
            private double altitude;
            private double pdop_accuracy;
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
}
