package com.tusi.qdcloudcontrol.net;

import android.util.Log;


import com.tusi.qdcloudcontrol.modle.event.MQMessageEvent;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;

/**
 * 使用EventBus分发事件
 *
 * @author LichFaker on 16/3/25.
 * @Email lichfaker@gmail.com
 */
public class MqttCallbackBus implements MqttCallback {
    private static final String TAG = "MqttCallbackBus";

    @Override
    public void connectionLost(Throwable cause) {
        Log.d(TAG, "connectionLost() called with: cause = [" + cause.getMessage() + "]");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) {
        EventBus.getDefault().post(new MQMessageEvent(topic, message));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
