package com.tusi.qdcloudcontrol.modle;

import java.util.List;

/**
 * Created by linfeng on 2018/10/17  15:45
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class SignalLampRes {


    /**
     * junction : {}
     * confidence : 1.0
     * entrances : [{"exits":[{"phase":{"RED":23,"YELLOW":3,"GREEN":45},"currentStatus":2,"countdown":14,"exitDirection":2},{"phase":{"RED":23,"YELLOW":3,"GREEN":45},"currentStatus":2,"countdown":14,"exitDirection":3},{"phase":{"RED":23,"YELLOW":3,"GREEN":45},"currentStatus":2,"countdown":14,"exitDirection":4}],"road":{"road":[{"startNode":"3_1_1","start2end":false}]},"connectingRoads":[{"currentStatus":2,"countdown":14,"mapConnecting":{"incommingRoad":0,"junctionId":0,"outgoingRoad":0,"connectingId":0}},{"currentStatus":2,"countdown":14,"mapConnecting":{"incommingRoad":0,"junctionId":0,"outgoingRoad":0,"connectingId":0}},{"currentStatus":2,"countdown":14,"mapConnecting":{"incommingRoad":0,"junctionId":0,"outgoingRoad":0,"connectingId":0}}]}]
     * timestamp : 1540199132344
     */

    private JunctionBean junction;
    private double confidence;
    private long timestamp;
    private List<EntrancesBean> entrances;

    public JunctionBean getJunction() {
        return junction;
    }

    public void setJunction(JunctionBean junction) {
        this.junction = junction;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public List<EntrancesBean> getEntrances() {
        return entrances;
    }

    public void setEntrances(List<EntrancesBean> entrances) {
        this.entrances = entrances;
    }

    public static class JunctionBean {
    }

    public static class EntrancesBean {
        /**
         * exits : [{"phase":{"RED":23,"YELLOW":3,"GREEN":45},"currentStatus":2,"countdown":14,"exitDirection":2},{"phase":{"RED":23,"YELLOW":3,"GREEN":45},"currentStatus":2,"countdown":14,"exitDirection":3},{"phase":{"RED":23,"YELLOW":3,"GREEN":45},"currentStatus":2,"countdown":14,"exitDirection":4}]
         * road : {"road":[{"startNode":"3_1_1","start2end":false}]}
         * connectingRoads : [{"currentStatus":2,"countdown":14,"mapConnecting":{"incommingRoad":0,"junctionId":0,"outgoingRoad":0,"connectingId":0}},{"currentStatus":2,"countdown":14,"mapConnecting":{"incommingRoad":0,"junctionId":0,"outgoingRoad":0,"connectingId":0}},{"currentStatus":2,"countdown":14,"mapConnecting":{"incommingRoad":0,"junctionId":0,"outgoingRoad":0,"connectingId":0}}]
         */

        private RoadBeanX road;
        private List<ExitsBean> exits;
        private List<ConnectingRoadsBean> connectingRoads;

        public RoadBeanX getRoad() {
            return road;
        }

        public void setRoad(RoadBeanX road) {
            this.road = road;
        }

        public List<ExitsBean> getExits() {
            return exits;
        }

        public void setExits(List<ExitsBean> exits) {
            this.exits = exits;
        }

        public List<ConnectingRoadsBean> getConnectingRoads() {
            return connectingRoads;
        }

        public void setConnectingRoads(List<ConnectingRoadsBean> connectingRoads) {
            this.connectingRoads = connectingRoads;
        }

        public static class RoadBeanX {
            private List<RoadBean> road;

            public List<RoadBean> getRoad() {
                return road;
            }

            public void setRoad(List<RoadBean> road) {
                this.road = road;
            }

            public static class RoadBean {
                /**
                 * startNode : 3_1_1
                 * start2end : false
                 */

                private String startNode;
                private boolean start2end;

                public String getStartNode() {
                    return startNode;
                }

                public void setStartNode(String startNode) {
                    this.startNode = startNode;
                }

                public boolean isStart2end() {
                    return start2end;
                }

                public void setStart2end(boolean start2end) {
                    this.start2end = start2end;
                }
            }
        }

        public static class ExitsBean {
            /**
             * phase : {"RED":23,"YELLOW":3,"GREEN":45}
             * currentStatus : 2
             * countdown : 14
             * exitDirection : 2
             */

            private PhaseBean phase;
            private int currentStatus;
            private int countdown;
            private int exitDirection;

            public PhaseBean getPhase() {
                return phase;
            }

            public void setPhase(PhaseBean phase) {
                this.phase = phase;
            }

            public int getCurrentStatus() {
                return currentStatus;
            }

            public void setCurrentStatus(int currentStatus) {
                this.currentStatus = currentStatus;
            }

            public int getCountdown() {
                return countdown;
            }

            public void setCountdown(int countdown) {
                this.countdown = countdown;
            }

            public int getExitDirection() {
                return exitDirection;
            }

            public void setExitDirection(int exitDirection) {
                this.exitDirection = exitDirection;
            }

            public static class PhaseBean {
                /**
                 * RED : 23
                 * YELLOW : 3
                 * GREEN : 45
                 */

                private int RED;
                private int YELLOW;
                private int GREEN;

                public int getRED() {
                    return RED;
                }

                public void setRED(int RED) {
                    this.RED = RED;
                }

                public int getYELLOW() {
                    return YELLOW;
                }

                public void setYELLOW(int YELLOW) {
                    this.YELLOW = YELLOW;
                }

                public int getGREEN() {
                    return GREEN;
                }

                public void setGREEN(int GREEN) {
                    this.GREEN = GREEN;
                }
            }
        }

        public static class ConnectingRoadsBean {
            /**
             * currentStatus : 2
             * countdown : 14
             * mapConnecting : {"incommingRoad":0,"junctionId":0,"outgoingRoad":0,"connectingId":0}
             */

            private int currentStatus;
            private int countdown;
            private MapConnectingBean mapConnecting;

            public int getCurrentStatus() {
                return currentStatus;
            }

            public void setCurrentStatus(int currentStatus) {
                this.currentStatus = currentStatus;
            }

            public int getCountdown() {
                return countdown;
            }

            public void setCountdown(int countdown) {
                this.countdown = countdown;
            }

            public MapConnectingBean getMapConnecting() {
                return mapConnecting;
            }

            public void setMapConnecting(MapConnectingBean mapConnecting) {
                this.mapConnecting = mapConnecting;
            }

            public static class MapConnectingBean {
                /**
                 * incommingRoad : 0
                 * junctionId : 0
                 * outgoingRoad : 0
                 * connectingId : 0
                 */

                private int incommingRoad;
                private int junctionId;
                private int outgoingRoad;
                private int connectingId;

                public int getIncommingRoad() {
                    return incommingRoad;
                }

                public void setIncommingRoad(int incommingRoad) {
                    this.incommingRoad = incommingRoad;
                }

                public int getJunctionId() {
                    return junctionId;
                }

                public void setJunctionId(int junctionId) {
                    this.junctionId = junctionId;
                }

                public int getOutgoingRoad() {
                    return outgoingRoad;
                }

                public void setOutgoingRoad(int outgoingRoad) {
                    this.outgoingRoad = outgoingRoad;
                }

                public int getConnectingId() {
                    return connectingId;
                }

                public void setConnectingId(int connectingId) {
                    this.connectingId = connectingId;
                }
            }
        }
    }
}
