package com.tusi.qdcloudcontrol;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
//import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.CrashReport;
import com.tusi.qdcloudcontrol.common.Constants;
import com.tusi.qdcloudcontrol.internal.di.components.AppComponent;
import com.tusi.qdcloudcontrol.internal.di.components.DaggerAppComponent;
import com.tusi.qdcloudcontrol.internal.di.modules.ApplicationModule;
import com.tusi.qdcloudcontrol.modle.RoadInfoHistory;
import com.tusi.qdcloudcontrol.net.MqttManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by linfeng on 2018/10/13  22:18
 * Email zhanglinfengdev@163.com
 *连接后端
 * @function details...
 */
public class QDApplication extends Application {

    private AppComponent mApplicationComponent;
    private static final String TAG = "QDApplication";

    @Named(Constants.CLIENT_1)
    @Inject
    MqttManager mqttManager1;

    @Named(Constants.CLIENT_2)
    @Inject
    MqttManager mqttManager2;

    @Named(Constants.QDHOME_DIR)
    @Inject
    public File mAppHomeDir;

    @Named(Constants.FAULT_CONFIG_DIR)
    @Inject
    public File mFaultConfigDir;

    public static ArrayList<RoadInfoHistory> roadInfoHistoryArrayList = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        // 第三个参数测试时候设成true ,发布时候设置成false。
        CrashReport.initCrashReport(getApplicationContext(), "794cf594d7", true);
        initSelf();
        initMap();
        try {
            initDataBaseFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initializeInject();
        mApplicationComponent.inject(this);
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=5bcb0ec9");
        releaseConfigFile();


    }

    private void releaseConfigFile() {
        String fileName = Constants.HOST_CONFIG;//hostConfig   tbox和cloud
        final File file1 = new File(mAppHomeDir, fileName);
        if (!file1.exists()) {
            try {
                file1.createNewFile();
                final String hostContent = ResourceUtils.readAssets2String(fileName);
                FileIOUtils.writeFileFromString(file1, hostContent);
            } catch (IOException e) {
                e.printStackTrace();
                ToastUtils.showShort("配置文件释放失败");
            }

        }


        final File file = new File(mFaultConfigDir, Constants.FAULT_CONFIG_FILENAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
                final String faultContnet = ResourceUtils.readAssets2String(Constants.FAULT_CONFIG_FILENAME);
                FileIOUtils.writeFileFromString(file, faultContnet);
            } catch (IOException e) {
                e.printStackTrace();
                ToastUtils.showShort("配置文件释放失败");
            }

        }

    }

    private void initDataBaseFile() throws Exception {
        File file = new File(getFilesDir(), Constants.DATA_BASE_INFO);
        if (!file.exists()) {
            InputStream open = getAssets().open(Constants.DATA_BASE_INFO);
            File dataFile = new File(getFilesDir(), Constants.DATA_BASE_INFO);
            boolean b = FileIOUtils.writeFileFromIS(dataFile, open);
        }
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file, null);
        Cursor road_rect = sqLiteDatabase.query("road_rect", null, null, null, null, null, null);
        if (road_rect.moveToFirst()) {
            for (int i = 0; i < road_rect.getCount(); i++) {
                String roadName = road_rect.getString(0);
                String partId = road_rect.getString(1);
                byte[] rgn_data = road_rect.getBlob(road_rect.getColumnIndex("rgn_data"));
                RoadInfoHistory roadInfoHistory = new RoadInfoHistory(roadName, partId, rgn_data);
                roadInfoHistoryArrayList.add(roadInfoHistory);
                road_rect.moveToNext();
            }
        }
    }

    private void initSelf() {
        Utils.init(this);
    }

    private void initializeInject() {
        mApplicationComponent = DaggerAppComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public AppComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    private void initMap() {
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
