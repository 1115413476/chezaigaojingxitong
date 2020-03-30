package com.tusi.qdcloudcontrol.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;

public class VoiceUtils {
    private MediaPlayer player;

    /*播放本地语音*/
    private void playLocalVoice(Context context, String videoName) {
        try {
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor afd = assetManager.openFd("voice/" + videoName);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setLooping(false);//循环播放
            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
