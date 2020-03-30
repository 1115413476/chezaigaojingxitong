package com.tusi.qdcloudcontrol.modle;

import java.util.List;

/**
 * Created by linfeng on 2018/10/17  15:22
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class BusRoadRes {


    /**
     * road : {"road":[{"vendor":"EMG","startNode":"","roadId":72,"endNode":"","start2end":true}],"endPosition":{"latitude":31.32641989,"longitude":121.22692037},"startPosition":{"latitude":31.32598671,"longitude":121.22949932}}
     * confidence : 0.9
     * lanes : [{"positiveLanes":1,"negativeLanes":0}]
     * restrictions : [{"restrictionType":1,"restrictedVehicles":2015,"startTime":1300,"endTime":1500,"whitelist":0}]
     * indate : 20181023
     */

    private RoadBeanX road;
    private double confidence;
    private String indate;
    private List<LanesBean> lanes;
    private List<RestrictionsBean> restrictions;

    public RoadBeanX getRoad() {
        return road;
    }

    public void setRoad(RoadBeanX road) {
        this.road = road;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public String getIndate() {
        return indate;
    }

    public void setIndate(String indate) {
        this.indate = indate;
    }

    public List<LanesBean> getLanes() {
        return lanes;
    }

    public void setLanes(List<LanesBean> lanes) {
        this.lanes = lanes;
    }

    public List<RestrictionsBean> getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(List<RestrictionsBean> restrictions) {
        this.restrictions = restrictions;
    }

    public static class RoadBeanX {
        /**
         * road : [{"vendor":"EMG","startNode":"","roadId":72,"endNode":"","start2end":true}]
         * endPosition : {"latitude":31.32641989,"longitude":121.22692037}
         * startPosition : {"latitude":31.32598671,"longitude":121.22949932}
         */

        private EndPositionBean endPosition;
        private StartPositionBean startPosition;
        private List<RoadBean> road;

        public EndPositionBean getEndPosition() {
            return endPosition;
        }

        public void setEndPosition(EndPositionBean endPosition) {
            this.endPosition = endPosition;
        }

        public StartPositionBean getStartPosition() {
            return startPosition;
        }

        public void setStartPosition(StartPositionBean startPosition) {
            this.startPosition = startPosition;
        }

        public List<RoadBean> getRoad() {
            return road;
        }

        public void setRoad(List<RoadBean> road) {
            this.road = road;
        }

        public static class EndPositionBean {
            /**
             * latitude : 31.32641989
             * longitude : 121.22692037
             */

            private double latitude;
            private double longitude;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }
        }

        public static class StartPositionBean {
            /**
             * latitude : 31.32598671
             * longitude : 121.22949932
             */

            private double latitude;
            private double longitude;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }
        }

        public static class RoadBean {
            /**
             * vendor : EMG
             * startNode :
             * roadId : 72
             * endNode :
             * start2end : true
             */

            private String vendor;
            private String startNode;
            private int roadId;
            private String endNode;
            private boolean start2end;

            public String getVendor() {
                return vendor;
            }

            public void setVendor(String vendor) {
                this.vendor = vendor;
            }

            public String getStartNode() {
                return startNode;
            }

            public void setStartNode(String startNode) {
                this.startNode = startNode;
            }

            public int getRoadId() {
                return roadId;
            }

            public void setRoadId(int roadId) {
                this.roadId = roadId;
            }

            public String getEndNode() {
                return endNode;
            }

            public void setEndNode(String endNode) {
                this.endNode = endNode;
            }

            public boolean isStart2end() {
                return start2end;
            }

            public void setStart2end(boolean start2end) {
                this.start2end = start2end;
            }
        }
    }

    public static class LanesBean {
        /**
         * positiveLanes : 1
         * negativeLanes : 0
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

    public static class RestrictionsBean {
        /**
         * restrictionType : 1
         * restrictedVehicles : 2015
         * startTime : 1300
         * endTime : 1500
         * whitelist : 0
         */

        private int restrictionType;
        private int restrictedVehicles;
        private int startTime;
        private int endTime;
        private int whitelist;

        public int getRestrictionType() {
            return restrictionType;
        }

        public void setRestrictionType(int restrictionType) {
            this.restrictionType = restrictionType;
        }

        public int getRestrictedVehicles() {
            return restrictedVehicles;
        }

        public void setRestrictedVehicles(int restrictedVehicles) {
            this.restrictedVehicles = restrictedVehicles;
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public int getWhitelist() {
            return whitelist;
        }

        public void setWhitelist(int whitelist) {
            this.whitelist = whitelist;
        }
    }
}
