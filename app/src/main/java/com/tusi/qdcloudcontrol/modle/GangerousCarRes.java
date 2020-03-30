package com.tusi.qdcloudcontrol.modle;

import java.util.List;

/**
 * Created by linfeng on 2018/10/17  15:49
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class GangerousCarRes {


    /**
     * road : {}
     * vehicles : [{"userBrake":5,"vehicleId":998886691,"position":{"latitude":31.33209647,"longitude":121.22972254},"userCutin":4,"timestamp":1540298412152},null,null,null,null]
     * targetCell : [{"cellid":10000,"circle":{"center":{"latitude":31.33209647,"longitude":121.22972254},"radius":30}},null,null,null,null]
     * uniqueId : 20181023204015.126
     */

    private RoadBean road;
    private String uniqueId;
    private List<VehiclesBean> vehicles;
    private List<TargetCellBean> targetCell;

    public RoadBean getRoad() {
        return road;
    }

    public void setRoad(RoadBean road) {
        this.road = road;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public List<VehiclesBean> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<VehiclesBean> vehicles) {
        this.vehicles = vehicles;
    }

    public List<TargetCellBean> getTargetCell() {
        return targetCell;
    }

    public void setTargetCell(List<TargetCellBean> targetCell) {
        this.targetCell = targetCell;
    }

    public static class RoadBean {
    }

    public static class VehiclesBean {
        /**
         * userBrake : 5
         * vehicleId : 998886691
         * position : {"latitude":31.33209647,"longitude":121.22972254}
         * userCutin : 4
         * timestamp : 1540298412152
         */

        private int userBrake = -1;
        private int vehicleId;
        private PositionBean position;
        private int userCutin = -1;
        private int userSpeed = -1;
        private int userinfraction = -1;
        private long timestamp;

        public int getUserSpeed() {
            return userSpeed;
        }

        public void setUserSpeed(int userSpeed) {
            this.userSpeed = userSpeed;
        }

        public int getUserinfraction() {
            return userinfraction;
        }

        public void setUserinfraction(int userinfraction) {
            this.userinfraction = userinfraction;
        }

        public int getUserBrake() {
            return userBrake;
        }

        public void setUserBrake(int userBrake) {
            this.userBrake = userBrake;
        }

        public int getVehicleId() {
            return vehicleId;
        }

        public void setVehicleId(int vehicleId) {
            this.vehicleId = vehicleId;
        }

        public PositionBean getPosition() {
            return position;
        }

        public void setPosition(PositionBean position) {
            this.position = position;
        }

        public int getUserCutin() {
            return userCutin;
        }

        public void setUserCutin(int userCutin) {
            this.userCutin = userCutin;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public static class PositionBean {
            /**
             * latitude : 31.33209647
             * longitude : 121.22972254
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
    }

    public static class TargetCellBean {
        /**
         * cellid : 10000
         * circle : {"center":{"latitude":31.33209647,"longitude":121.22972254},"radius":30}
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
             * center : {"latitude":31.33209647,"longitude":121.22972254}
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
                 * latitude : 31.33209647
                 * longitude : 121.22972254
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
        }
    }
}
