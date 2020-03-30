package com.tusi.qdcloudcontrol.modle.event;

/**
 * Created by linfeng on 2018/10/17  14:41
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class Event<T> {
    public int code;
    public T data;

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
