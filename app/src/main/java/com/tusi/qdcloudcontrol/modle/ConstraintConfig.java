package com.tusi.qdcloudcontrol.modle;

/**
 * Created by linfeng on 2018/10/30  12:39
 * Email zhanglinfengdev@163.com
 * Function details...
 * 对于事件的一些限制条件，如时间等
 */
public class ConstraintConfig {

    /**
     * alertEvent : {"distance":{"roadSkid":150,"roadConstruction":150,"roadObstructions":150,"busLanes":100}}
     * signalLamp : {"distance":200}
     * messageQueue : {"timeout_queue":60,"timeout_mesage":2}
     */

    private AlertEventBean alertEvent;
    private SignalLampBean signalLamp;
    private MessageQueueBean messageQueue;

    public AlertEventBean getAlertEvent() {
        return alertEvent;
    }

    public void setAlertEvent(AlertEventBean alertEvent) {
        this.alertEvent = alertEvent;
    }

    public SignalLampBean getSignalLamp() {
        return signalLamp;
    }

    public void setSignalLamp(SignalLampBean signalLamp) {
        this.signalLamp = signalLamp;
    }

    public MessageQueueBean getMessageQueue() {
        return messageQueue;
    }

    public void setMessageQueue(MessageQueueBean messageQueue) {
        this.messageQueue = messageQueue;
    }

    public static class AlertEventBean {
        /**
         * distance : {"roadSkid":150,"roadConstruction":150,"roadObstructions":150,"busLanes":100}
         */

        private DistanceBean distance;

        public DistanceBean getDistance() {
            return distance;
        }

        public void setDistance(DistanceBean distance) {
            this.distance = distance;
        }

        public static class DistanceBean {
            /**
             * roadSkid : 150
             * roadConstruction : 150
             * roadObstructions : 150
             * busLanes : 100
             */

            private int roadSkid = 150;
            private int roadConstruction = 150;
            private int roadObstructions = 150;
            private int busLanes = 100;

            public int getRoadSkid() {
                return roadSkid;
            }

            public void setRoadSkid(int roadSkid) {
                this.roadSkid = roadSkid;
            }

            public int getRoadConstruction() {
                return roadConstruction;
            }

            public void setRoadConstruction(int roadConstruction) {
                this.roadConstruction = roadConstruction;
            }

            public int getRoadObstructions() {
                return roadObstructions;
            }

            public void setRoadObstructions(int roadObstructions) {
                this.roadObstructions = roadObstructions;
            }

            public int getBusLanes() {
                return busLanes;
            }

            public void setBusLanes(int busLanes) {
                this.busLanes = busLanes;
            }
        }
    }

    public static class SignalLampBean {
        /**
         * distance : 200
         * TODO 先改成500看
         */

        private int distance = 500;

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }
    }

    public static class MessageQueueBean {
        /**
         * timeout_queue : 60
         * timeout_mesage : 2
         */

        private int timeout_queue = 60;
        private int timeout_mesage = 2;

        public int getTimeout_queue() {
            return timeout_queue;
        }

        public void setTimeout_queue(int timeout_queue) {
            this.timeout_queue = timeout_queue;
        }

        public int getTimeout_mesage() {
            return timeout_mesage;
        }

        public void setTimeout_mesage(int timeout_mesage) {
            this.timeout_mesage = timeout_mesage;
        }
    }
}
