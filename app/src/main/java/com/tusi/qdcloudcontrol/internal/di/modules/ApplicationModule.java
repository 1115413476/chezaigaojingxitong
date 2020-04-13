package com.tusi.qdcloudcontrol.internal.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.google.gson.Gson;
import com.tusi.qdcloudcontrol.BuildConfig;
import com.tusi.qdcloudcontrol.QDApplication;
import com.tusi.qdcloudcontrol.common.Constants;
import com.tusi.qdcloudcontrol.modle.ConstraintConfig;
import com.tusi.qdcloudcontrol.modle.QDHostModule;
import com.tusi.qdcloudcontrol.net.MqttManager;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by linfeng on 2018/10/15  12:31
 * Email zhanglinfengdev@163.com
 * Function details...
 */
@Module
public class ApplicationModule {
    private static final String TAG = "ApplicationModule";
    private final QDApplication mApplication;

    public ApplicationModule(QDApplication application) {
        this.mApplication = application;
    }

    @Provides
    @Singleton
    public Context provideApplicatonContext() {
        return this.mApplication;
    }

    @Provides
    @Singleton
    public QDApplication provideQDApplication() {
        return this.mApplication;
    }

    @Named(Constants.CLIENT_1)
    @Singleton
    @Provides
    public MqttManager provideMqttManager1(QDHostModule qdHostModule, Context context) {
        MqttManager mqttManager = MqttManager.getInstance();
        String host = null;

        Log.d(TAG, "provideMqttManager1() called with: qdHostModule = [" + qdHostModule + "]");
        if (qdHostModule != null && !TextUtils.isEmpty(qdHostModule.getTbox())) {
//        if (false) {
            host = qdHostModule.getTbox();
        } else {
            host = BuildConfig.MQTT_HOST;//public static final String MQTT_HOST = "tcp://120.133.21.14:11883";
                                           // public static final String MQTT_HOST_1 = "tcp://120.133.21.14:11883";
        }
        host = BuildConfig.MQTT_HOST;
        final String clientId = fetchClientId(context);
        Log.d(TAG, "provideMqttManager1() called with: host = [" + host + "]");
        final boolean connect = mqttManager.creatConnect(host, Constants.USER_NAME, Constants.PASSWORD, clientId);
        if (connect) {
            Log.d(TAG, "provideMqttManager1()连接成功");//true
        } else {
            Log.d(TAG, "provideMqttManager1()连接失败");
        }
        return mqttManager;
    }

    @Named(Constants.CLIENT_2)
    @Singleton
    @Provides
    public MqttManager provideMqttManager2(QDHostModule qdHostModule, Context context) {
        MqttManager mqttManager = MqttManager.getInstance();
        String host = null;
        Log.d(TAG, "provideMqttManager2() called with: qdHostModule = [" + qdHostModule + "]");
        if (qdHostModule != null && !TextUtils.isEmpty(qdHostModule.getCloud())) {
//        if (false) {
            host = qdHostModule.getCloud();
        } else {
            host = BuildConfig.MQTT_HOST_1;
        }
        host = BuildConfig.MQTT_HOST_1;
        final String clientId = fetchClientId(context);
        Log.d(TAG, "provideMqttManager2() called with: host = [" + host + "]");
        final boolean connect = mqttManager.creatConnect(host, Constants.USER_NAME, Constants.PASSWORD, clientId);
        if (connect) {
            Log.d(TAG, "provideMqttManager2()连接成功");//true
        } else {
            Log.d(TAG, "provideMqttManager2()连接失败");
        }
        return mqttManager;
    }

    @Nullable
    private String fetchClientId(Context context) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
        final String oldAndroidClient = sharedPreferences.getString(Constants.SPKEY_CLIENT_ID, "");
        if (TextUtils.isEmpty(oldAndroidClient)) {
            final String androidCliennt = String.format("%s_%d", "AndroidClient", System.currentTimeMillis());
            sharedPreferences.edit().putString(Constants.SPKEY_CLIENT_ID, androidCliennt).commit();
            return androidCliennt;
        } else {
            return oldAndroidClient;
        }
    }

    //逐层创建文件夹imgs等等
    @Singleton
    @Provides
    public QDHostModule provideHostModule(@Named(Constants.QDHOME_DIR) File homeDir,Gson gson) {
        File hostFile = new File(homeDir, Constants.HOST_CONFIG);
        new File(homeDir, "hostConfig.json").delete();
        if (!hostFile.exists()) {
            return new QDHostModule();
        }
        final QDHostModule qdHostModule = gson.fromJson(FileIOUtils.readFile2String(hostFile), QDHostModule.class);
        return qdHostModule;
    }

    @Named(Constants.QDHOME_DIR)
    @Singleton
    @Provides
    public File provideAppHomeDir() {
        File mAppHomeDir = new File(Environment.getExternalStorageDirectory(), Constants.QDHOME_DIR);
        if (!mAppHomeDir.exists() || !mAppHomeDir.isDirectory()) {
            mAppHomeDir.mkdirs();
        }
        return mAppHomeDir;
    }

    @Named(Constants.FAULT_CONFIG_DIR)
    @Singleton
    @Provides
    public File provideFaultConfigDir(@Named(Constants.QDHOME_DIR) File homeDir) {
        File mFaultConfigDir = new File(homeDir, Constants.FAULT_CONFIG_DIR);
        if (!mFaultConfigDir.exists() || !mFaultConfigDir.isDirectory()) {
            mFaultConfigDir.mkdirs();
        }
        return mFaultConfigDir;
    }

    @Named(Constants.FAULT_CONFIG_IMGS_DIR)
    @Singleton
    @Provides
    public File provideFaultConfigImgDir(@Named(Constants.FAULT_CONFIG_DIR) File faultDir) {
        File mFaultConfigImgsDir = new File(faultDir, Constants.FAULT_CONFIG_IMGS_DIR);
        if (!mFaultConfigImgsDir.exists() || !mFaultConfigImgsDir.isDirectory()) {
            mFaultConfigImgsDir.mkdirs();
        }
        return mFaultConfigImgsDir;
    }

    @Singleton
    @Provides
    public ConstraintConfig providerConstraintConfig(@Named(Constants.QDHOME_DIR) File homeDir, Gson gson) {
        final File file = new File(homeDir, Constants.CONSTRAINT_CONFIG);
        String constraintJson = null;
        if (!file.exists() || file.isDirectory()) {
            final String s = ResourceUtils.readAssets2String(Constants.CONSTRAINT_CONFIG);
            FileIOUtils.writeFileFromString(file, s);
            constraintJson = s;
        } else {
            constraintJson = FileIOUtils.readFile2String(file);
        }
        final ConstraintConfig constraintConfig = gson.fromJson(constraintJson, ConstraintConfig.class);
        return constraintConfig;
    }

    @Singleton
    @Provides
    public Gson providerGson() {
        return new Gson();
    }

}
