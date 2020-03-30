package com.tusi.qdcloudcontrol.internal.di.components;

import android.content.Context;

import com.google.gson.Gson;
import com.tusi.qdcloudcontrol.QDApplication;
import com.tusi.qdcloudcontrol.activity.BaseActivity;
import com.tusi.qdcloudcontrol.common.Constants;
import com.tusi.qdcloudcontrol.internal.di.modules.ApplicationModule;
import com.tusi.qdcloudcontrol.manager.VoiceManager;
import com.tusi.qdcloudcontrol.modle.ConstraintConfig;
import com.tusi.qdcloudcontrol.modle.QDHostModule;
import com.tusi.qdcloudcontrol.net.MqttManager;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by linfeng on 2018/10/15  10:29
 * Email zhanglinfengdev@163.com
 * Function details...
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface AppComponent {
    void inject(BaseActivity activity);

    void inject(QDApplication application);

    Context contexet();

    QDApplication qdApplication();

    @Named(Constants.CLIENT_1)
    MqttManager mqtManager1();

    @Named(Constants.CLIENT_2)
    MqttManager mqtManager2();

    @Named(Constants.QDHOME_DIR)
    File appHomeDir();

    @Named(Constants.FAULT_CONFIG_DIR)
    File appFaultConfigDir();

    @Named(Constants.FAULT_CONFIG_IMGS_DIR)
    File appFaultConfigImgsDir();

    QDHostModule hostModle();

    ConstraintConfig constraingConfig();

    VoiceManager voiceManager();

    Gson gson();


}

