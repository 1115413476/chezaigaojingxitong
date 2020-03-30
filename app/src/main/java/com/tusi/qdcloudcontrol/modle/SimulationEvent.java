package com.tusi.qdcloudcontrol.modle;

/**
 * Created by linfeng on 2018/11/1  16:46
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class SimulationEvent {
    public SimulationEvent(String eventName, String eventIcon, String topic, String content) {
        EventName = eventName;
        EventIcon = eventIcon;
        Topic = topic;
        Content = content;
    }

    /**
     * EventName : 道路施工
     * EventIcon : icon所在路径根据APP的设计设置
     * Topic : vpub/perception/pavementAnomaly
     * Content : [
     {
     "vehicleId":998886703,
     "eventType":10,
     "geoInfo":{
     "vendor":"图商代码",
     "position":{
     "longitude":95.01232752,
     "latitude":64.82829641,
     "altitude":20.973882,
     "pdop_accuracy":99.8,
     "hdop_accuracy":98.3,
     "vdop_accuracy":0.9
     },
     "roadId":34,
     "affectedLanes":[
     {
     "positiveLanes":343,
     "negativeLanes":213
     }
     ]
     },
     "dangerLevel":1,
     "timestamp":1540192794085
     }
     ]
     */



    private String EventName;
    private String EventIcon;
    private String Topic;
    private String Content;

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public String getEventIcon() {
        return EventIcon;
    }

    public void setEventIcon(String EventIcon) {
        this.EventIcon = EventIcon;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String Topic) {
        this.Topic = Topic;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }
}
