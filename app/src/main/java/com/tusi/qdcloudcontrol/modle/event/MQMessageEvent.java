package com.tusi.qdcloudcontrol.modle.event;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by linfeng on 2018/10/17  14:42
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class MQMessageEvent extends Event<MqttMessage> {
    private String topic;

    public MQMessageEvent(String topic, MqttMessage message) {
        this.topic = topic;
        this.data = message;
    }

    public String getTopic() {
        return topic;
    }
}
