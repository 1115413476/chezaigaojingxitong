package com.tusi.qdcloudcontrol.common;

/**
 * Created by linfeng on 2018/10/15  16:18
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class Constants {

    public static final float DEFAULT_LEVEL = 19.5f;
    public static final String CLIENT_1 = "host_1";// mMqttManager1
    public static final String CLIENT_2 = "host_2";
    public static final String HOST_CONFIG = "hostConfig.txt";
    public static final String CONSTRAINT_CONFIG = "constraintConfig.txt";
    public static final String DATA_BASE_INFO = "road.db";
    public static final String ENAME_DAO_LU_SHI_GSON = "道路施工";
    public static final String ENAME_DAO_LU_DA_HUA = "道路打滑";
    public static final String ENAME_DONG_JIAO_CHE_DAO = "公交专用道";
    public static final String ENAME_WEI_XIAN_CHE_LIANG = "危险车辆";
    public static final String ENAME_ZHANG_AI_WU = "有障碍物";
    public static final String SPKEY_CLIENT_ID = "spKeyMqttClienntId";
    public static String PASSWORD = null;
    public static String CLIENT_ID = "AndroidClient";
    public static String USER_NAME =null;// "admin";

    public static final int DEFAULT_MQTT_QOS = 2;

    //请求车辆vehicleId
    public static final String SEND_TOPIC_VEHICLE_ID = "tbox/request/yktype";
    //道路施工/道路结冰（DM0109_路面条件异常）
    public static final String SEND_TOPIC_ROAD_EXCEPTION = "vpub/perception/pavementAnomaly";
    //障碍物提醒（DM0110_障碍物感知）
    public static final String SEND_TOPIC_ROAD_BARRIERS = "vpub/perception/obstacle";

    //上车
    public static final String SEND_TOPIC_ONCAR = "/app/onCar/";

    //下车
    public static final String SEND_TOPIC_OFFCAR = "/app/offCar/";

    //获取起始点坐标
    public static String RECEIVE_START_DESTIONPOINT = "/app/%s/destion/";
    //推送行驶路径到后台
    public static final String SEND_MESSAGE_TOSERVER = "/app/route/";

    //车辆vehicleId
    public static final String RECEIVE_TOPIC_VEHICLE_ID = "tbox/reply/yktype";
    //道路施工/道路打滑DM0202_路面异常事件信息）placeholder=vehicleId
    public static String RECEIVE_TOPIC_ROAD_EXCEPITON = "cpub/%s/road/anomaly";
    //有障碍物（DM0203_路面障碍异常信息）placeholder=vehicleId
    public static String RECEIVE_TOPIC_ROAD_BARRIERS = "cpub/%s/road/obstacle";
    //公交专用道（DM0208_动态车道规划）
    public static final String RECEIVE_TOPIC_BUS_ROAD = "cpub/traffic/tidal";
    //危险车辆（DM0205_旁车信息）
    public static String RECEIVE_TOPIC_GANGEROUS_CAR = "cpub/%s/info";
    //信号灯（DM0211_信号灯实时状态）
    public static String RECEIVE_TOPIC_SIGNALLAMP = "cpub/%s/traffic/signal";
    //GPS数据
    public static final String RECEIVE_TOPIC_CAR_GPS = "tbox/send/position";


    public static final String QDHOME_DIR = "QDCloudControl";
    public static final String FAULT_CONFIG_DIR = "faultConfig";
    public static final String FAULT_CONFIG_FILENAME = "faultConfig2.txt";
    public static final String FAULT_CONFIG_IMGS_DIR = "imgs";


}
