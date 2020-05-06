package com.tusi.qdcloudcontrol.manager;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.SpeechSynthesizer;
import com.tusi.qdcloudcontrol.modle.event.SpeakComplateEvent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by zhanglinfeng on 2018/10/31
 * Copyright 2016
 * 使用科大讯飞TTS技术
 */
@Singleton
public class VoiceManager {

    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 默认发音人
    private String voicer = "xiaoyan";
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    @Inject
    public VoiceManager(Context context){
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);
    }


    public void startSpeaking(String content) {
        // 设置参数
        setParam();
        int code = mTts.startSpeaking(content, mTtsListener);
        //			/**
        //			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
        //			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
        //			*/
            /*String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
			int code = mTts.synthesizeToUri(text, path, mTtsListener);*/

        if (code != ErrorCode.SUCCESS) {
            EventBus.getDefault().post(new SpeakComplateEvent());
        }
    }

    public void stopSpeaking() {
        if (mTts != null) {
            mTts.stopSpeaking();
        }
    }

    public boolean isSpeaking() {
        if (mTts != null) {
            return mTts.isSpeaking();
        }
        return false;
    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private com.iflytek.cloud.SynthesizerListener mTtsListener = new com.iflytek.cloud.SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
//            showTip("开始播放");
        }

        @Override
        public void onSpeakPaused() {
//            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
//            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            //            mPercentForBuffering = percent;
            //            showTip(String.format(getString(R.string.tts_toast_format),
            //                    mPercentForBuffering, mPercentForPlaying));
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
            //            mPercentForPlaying = percent;
            //            showTip(String.format(getString(R.string.tts_toast_format),
            //                    mPercentForBuffering, mPercentForPlaying));
            //
            //            SpannableStringBuilder style = new SpannableStringBuilder(texts);
            //            Log.e(TAG, "beginPos = " + beginPos + "  endPos = " + endPos);
            //            style.setSpan(new BackgroundColorSpan(Color.RED), beginPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //            ((EditText) findViewById(R.id.tts_text)).setText(style);
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
//                showTip("播放完成");
            } else if (error != null) {
//                showTip(error.getPlainDescription(true));
            }
            EventBus.getDefault().post(new SpeakComplateEvent());
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}

            if (SpeechEvent.EVENT_TTS_BUFFER == eventType) {
                byte[] buf = obj.getByteArray(SpeechEvent.KEY_EVENT_TTS_BUFFER);
                Log.e("MscSpeechLog", "buf is =" + buf);
            }

        }
    };

    private void showTip(final String str) {
        ToastUtils.showShort(str);
    }

    /**
     * 参数设置
     *
     * @return
     */
    private void setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            mTts.setParameter(SpeechConstant.TTS_DATA_NOTIFY, "1");
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50");
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50");
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50");
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "pcm");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts.pcm");
    }

    public void release(){
            if (null != mTts) {
                mTts.stopSpeaking();
                // 退出时释放连接
                mTts.destroy();
        }
    }
}
