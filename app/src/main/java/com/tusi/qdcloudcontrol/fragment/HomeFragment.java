package com.tusi.qdcloudcontrol.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.baidu.location.a.i;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.ResourceUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.androidanimations.library.fading_entrances.FadeInAnimator;
import com.daimajia.androidanimations.library.fading_exits.FadeOutAnimator;
import com.daimajia.androidanimations.library.sliders.SlideInRightAnimator;
import com.daimajia.androidanimations.library.sliders.SlideOutRightAnimator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tusi.qdcloudcontrol.R;
import com.tusi.qdcloudcontrol.common.Constants;
import com.tusi.qdcloudcontrol.manager.VoiceManager;
import com.tusi.qdcloudcontrol.modle.AlertMessage;
import com.tusi.qdcloudcontrol.modle.BusRoadRes;
import com.tusi.qdcloudcontrol.modle.CarLatLngRes;
import com.tusi.qdcloudcontrol.modle.ConstraintConfig;
import com.tusi.qdcloudcontrol.modle.RoadInfoHistory;
import com.tusi.qdcloudcontrol.modle.RoadSkidRes;
import com.tusi.qdcloudcontrol.modle.RouteLineDataReq;
import com.tusi.qdcloudcontrol.modle.SignalGpsMode;
import com.tusi.qdcloudcontrol.modle.SimulationEvent;
import com.tusi.qdcloudcontrol.modle.StartDestinationRes;
import com.tusi.qdcloudcontrol.modle.GangerousCarRes;
import com.tusi.qdcloudcontrol.modle.OffCarReq;
import com.tusi.qdcloudcontrol.modle.OnCarReq;
import com.tusi.qdcloudcontrol.modle.RoadBarriersRes;
import com.tusi.qdcloudcontrol.modle.RoadConstructionRes;
import com.tusi.qdcloudcontrol.modle.RoadLineData;
import com.tusi.qdcloudcontrol.modle.SignalLampRes;
import com.tusi.qdcloudcontrol.modle.VehicleIdReq;
import com.tusi.qdcloudcontrol.modle.VehicleIdRes;
import com.tusi.qdcloudcontrol.modle.event.MQMessageEvent;
import com.tusi.qdcloudcontrol.inter.QDMapStatusChangeListener;
import com.tusi.qdcloudcontrol.internal.di.components.DataComponent;
import com.tusi.qdcloudcontrol.modle.event.SpeakComplateEvent;
import com.tusi.qdcloudcontrol.net.MqttManager;
import com.tusi.qdcloudcontrol.utils.overlayutils.DrivingRouteOverlay;
import com.tusi.qdcloudcontrol.view.SignalLampView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
/**
 * Created by linfeng on 2018/10/15  14:25
 * Email zhanglinfengdev@163.com
 * Function details...
 * changed by qianjiyin on 2019
 * Email qianjiyin@buaa.edu.cn
 * 主文件，绑定相关控件在第1274行
 * 信号灯隐藏hideSignalLampViews1174行
 * 1501行设置仿真配置
 */
public class HomeFragment extends BaseFragment implements QDSupportMapFragment.MapViewCreatedFinishCallback {
    private static final String TAG = "HomeFragment";
    private static final int WHAT_UPDATE_CAR_LOCATION = 0;
    private static final int WHAT_ALERT_MESSAGE_QUEUE = 1;
    private static final long DELAY_UPDATE_CAR_LOCATION = 1000;
    private static final long DELAY_HANDLE_ALERT_MESSAGE = 600;
    private BaiduMap mBaiduMap;
    private LinearLayout mLlAlertMessageRoot;
    private LinearLayout mLlAlertMessageTop1;
    private TextView mTvAlertMessageTop2;
    private TextView mTvAlertMessageTop3;
    private SensorManager mSensorManager;
    //    private SensorEventListener mSensorEventListener;
    private View mIvCompass;
    private RadioGroup mRgModes;
    private int mLastTimeCheckedId;
    private RecyclerView mRvEventList;
    private EventListAdaptr mEventsAdapter;
    private View mLlOnOrOffCar;
    private SignalLampView mSlvDown;
    private SignalLampView mSlvLeft;
    private SignalLampView mSlvTop;
    private SignalLampView mSlvRight;
    private View mLlSignalLampWrapper;
    //private boolean zoomflag=true;
    @Named(Constants.CLIENT_1)
    @Inject
    MqttManager mMqttManager1;//192.168.1.99    mMqttManager1.getHost() 120.133.21.14:11883 （hostconfig中

    @Named(Constants.CLIENT_2)//同上主要处理上下车和起始点的信息
    @Inject
    MqttManager mMqttManager2;// 10.0.1.25

    @Named(Constants.FAULT_CONFIG_DIR)
    @Inject
    File mFaultConfigDir;

    @Named(Constants.FAULT_CONFIG_IMGS_DIR)
    @Inject
    File mFaultConfigImgsDir;

    @Inject
    ConstraintConfig mConstraintConfig;

    @Inject
    VoiceManager mTtsManager;
    @Inject
    Gson mGson;

    private String mVehicleId;
    private List<AlertMessage> messageQueue = new ArrayList<AlertMessage>();

    private boolean isUpdateCarLocation;
    static List<String> signaGpsList = new ArrayList<>();
    static List<String[]> signGpsArray = new ArrayList<>();

    static {
        signaGpsList = ResourceUtils.readAssets2List("signalGpsInfo.txt");
        for (String string : signaGpsList) {
            String[] split = string.replace("(", "").replace(")", "").split(",");
            signGpsArray.add(split);
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
         //  Log.v("msg",msg.toString()+" 间隔 "+msg.what);//一秒2~3
            switch (msg.what) {
                case WHAT_UPDATE_CAR_LOCATION://0，位置
                    isUpdateCarLocation = true;
                    mHandler.sendEmptyMessageDelayed(WHAT_UPDATE_CAR_LOCATION, DELAY_UPDATE_CAR_LOCATION);
                    updateDebugInfo();
                    reConnect();
                    checkSignalLampTimeOut();
                    break;
                case WHAT_ALERT_MESSAGE_QUEUE://1，对列
                    if (messageQueue.size() > 0) {

                        mHandler.removeMessages(WHAT_ALERT_MESSAGE_QUEUE);
                        cleanTimeOutMessageandNullLoadInfor(messageQueue);
                        mLlAlertMessageRoot.setVisibility(View.INVISIBLE);//
                        Collections.sort(messageQueue, (AlertMessage o1, AlertMessage o2) -> {
                            final int i = o2.level - o1.level;
                            return i == 0 ? ((int) (o1.receiveTime - o2.receiveTime)) : i;
                        });

                        //未播报 播报时间小于等于2秒 最高优先级的三个事件
                        if (messageQueue.size() >= 3) {
                            Log.v(TAG,"messageQueue.size() > 3");
                            final AlertMessage temp3 = messageQueue.remove(0);
                            final AlertMessage temp2 = messageQueue.get(0);
                            final AlertMessage temp1 = messageQueue.get(1);
                            updateAlertMessagePanel(temp3, temp2, temp1);
//                            messageQueue.add(temp1);
//                            messageQueue.add(temp2);
                            messageQueue.add(temp3);
                        } else if (messageQueue.size() >= 2) {
                            Log.v(TAG,"messageQueue.size() = 2");
                            final AlertMessage temp2 = messageQueue.remove(0);
                            final AlertMessage temp1 = messageQueue.get(0);
                            updateAlertMessagePanel(temp2, temp1, null);
//                            messageQueue.add(temp1);
                            messageQueue.add(temp2);
                        } else if (messageQueue.size() >= 1) {
                            Log.v(TAG,"messageQueue.size() = 1");
                            final AlertMessage temp1 = messageQueue.remove(0);
                            updateAlertMessagePanel(temp1, null, null);
                            messageQueue.add(temp1);
                        } else {
                            Log.v(TAG,"messageQueue.size()>4");
                            mHandler.removeMessages(WHAT_ALERT_MESSAGE_QUEUE);
                            mHandler.sendEmptyMessage(WHAT_ALERT_MESSAGE_QUEUE);
                        }
                    } else {
                        Log.v(TAG,"messageQueue.size()<=0");
                        mHandler.sendEmptyMessageDelayed(WHAT_ALERT_MESSAGE_QUEUE, DELAY_HANDLE_ALERT_MESSAGE);
                        mLlAlertMessageRoot.setVisibility(View.INVISIBLE);//
                    }
                    break;
                    //少default
                    // default:  Log.v("error","error default");
            }

        }
    };
    private String mFaultConfigJsonStr;
    private static Map<String, Bitmap> mFaultImages = new HashMap<String, Bitmap>();

    private void checkSignalLampTimeOut() {
        if (lastReceiveSignalLampTime > 0 && (System.currentTimeMillis() - lastReceiveSignalLampTime) > TimeUnit.SECONDS.toMillis(5)) {
            hideSignalLampViews();
        }
    }

    private TextView mTvCurrentRoadId;
    private TextView mTvCurrentVersion;
    private long lastReceiveSignalLampTime;

    private void reConnect() {
        if (!mMqttManager1.isConnect()) {
            mMqttManager1.doConnect();
            Log.v("msg","1重连" + mMqttManager1);//断开则一直执行
        }

//        if (!mMqttManager2.isConnect()) {
//            //先不连2,2和1一样的
//        //    mMqttManager2.doConnect();
//            Log.v("msg","2重连"+ mMqttManager2);//上面不执行则一直执行下面的
//        }
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

    private void updateDebugInfo() {
        if (mTboxStatus != null && mMqttManager1 != null) {
            mTboxStatus.setText(String.format("TBox:[%s] Connected:[%s] ClientId:[%s]", mMqttManager1.getHost(), mMqttManager1.isConnect(), fetchClientId(this.getContext())));
        }
        if (mCloudStatus != null && mMqttManager2 != null) {
            //test
            mCloudStatus.setText(String.format("Cloud:[%s] Connected:[%s] ClientId:[%s]", mMqttManager2.getHost(), mMqttManager2.isConnect(), fetchClientId(this.getContext())));

        }
        if (mTvCurrentVehicleId != null) {
            mTvCurrentVehicleId.setText(String.format("CurrentVehicleId:[%s]", !TextUtils.isEmpty(this.mVehicleId) ? mVehicleId : "NULL"));
        }
        if (mTvCurrentOrderId != null) {
            mTvCurrentOrderId.setText(String.format("CurrentOrderId:[%s]", !TextUtils.isEmpty(this.mOrderId) ? mOrderId : "NULL"));
        }
        if (lastTimeCarLatLng != null) {
            final RoadInfoHistory roadInfoHistory = RoadInfoHistory.getRoadInfoHistory(new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude()));
            final String roadName = (roadInfoHistory != null && !TextUtils.isEmpty(roadInfoHistory.getRoadName())) ? roadInfoHistory.getRoadName() : "NULL";
            final String partId = (roadInfoHistory != null && !TextUtils.isEmpty(roadInfoHistory.getPartId())) ? roadInfoHistory.getPartId() : "NULL";
            mTvCurrentRoadId.setText(String.format("CurrentRoadId:[%s] PartId:[%s]", roadName, partId));
//            Log.d(TAG, "updateDebugInfo: RoadLine=" + roadInfoHistory.getRoadLine(new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude())));
        }

        mTvCurrentVersion.setText(String.format("CurrentVersion:[%s]", AppUtils.getAppVersionName()));
    }

    private View mllAlertMessageTop1Backgrounnd;
    private HashMap<Integer, Integer> sounddata;
    private SoundPool sp;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private TextView mTboxStatus;
    private TextView mCloudStatus;
    private TextView mTvCurrentVehicleId;
    private TextView mTvCurrentOrderId;

    /**
     * 清除一些因超时失效的事件
     * 事件进入列表超过五分钟  已经播放完毕
     * 事件进入队列时间内超过五分钟那个弄 更新时间超过2秒
     *
     * @param messageQueue
     */
    private void cleanTimeOutMessageandNullLoadInfor(List<AlertMessage> messageQueue) {
        final long timeMillis = System.currentTimeMillis();

      // final long timeMillis = 1540295822815L;
        final ArrayList<AlertMessage> timeOutMessage = new ArrayList<>();
        for (AlertMessage alertMessage : messageQueue) {
            final boolean isPlayed = alertMessage.isPlayed;
            final boolean timeOut = timeMillis - alertMessage.enqueueTime > mConstraintConfig.getMessageQueue().getTimeout_queue()*1000;
            final boolean palyedTime = timeMillis - alertMessage.receiveTime > mConstraintConfig.getMessageQueue().getTimeout_mesage()*1000;
            Log.v(TAG,"timeOut:"+timeOut+" "+"palyedTime:"+palyedTime+" time"+mConstraintConfig.getMessageQueue().getTimeout_queue());//timeOut:true palyedTime:true time60
            if (timeOut && (isPlayed || palyedTime)) {
                timeOutMessage.add(alertMessage);
            }
          /*  危险车辆 危险车辆位置一定要有
          if (alertMessage.mEventName.equals(Constants.ENAME_WEI_XIAN_CHE_LIANG)) {
                if ((RoadInfoHistory.getRoadInfoHistory(new LatLng(alertMessage.mLat, alertMessage.mLon)) == null) || (RoadInfoHistory.getRoadInfoHistory(new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude())) == null)) {
                    timeOutMessage.add(alertMessage);
                }
            }*/
        }
        messageQueue.removeAll(timeOutMessage);
    }

    private TextView mTvTop1LevelName;
    private TextView mTvTop1Name;
    private ImageView mIvMessageIcon;
    private String mShowingSignalLampStartNodeId;

    //警告事件布局
    private void updateAlertMessagePanel(AlertMessage alertMessage_level1, AlertMessage remove_level2, AlertMessage message_level3) {
        mLlAlertMessageRoot.setVisibility(View.VISIBLE);
        final long timeMillis = System.currentTimeMillis();//TODO 仿真修改为具体时间
        // final long timeMillis = 1540295822815L;
        if (alertMessage_level1 != null) {
            final long l = timeMillis - alertMessage_level1.receiveTime;//事件时间差，当前系统时间-事件时间
            Log.v(TAG,"alertMessage_level1 != null"+alertMessage_level1.mEventName);
            if (l <= 60 * 1000) {
            //    if (l <= 10 * 1000) {     //10s内的
                if (!alertMessage_level1.isPlayed) {
                    Log.v(TAG,"alertMessage_level1.isPlayed != null");
//                    if (mLlAlertMessageTop1.getVisibility() == View.VISIBLE) {
//                        outAnimationn(mLlAlertMessageTop1);
//                    }
//                    mHandler.postDelayed(() -> {
                    mLlAlertMessageTop1.setVisibility(View.VISIBLE);
//                    inAnimationn(mLlAlertMessageTop1);
                    mTvTop1Name.setText(alertMessage_level1.mEventName);
                    mTvTop1LevelName.setText(alertMessage_level1.getLevelName());
                    mIvMessageIcon.setImageResource(alertMessage_level1.getMessageIconByMsgName());
                    mllAlertMessageTop1Backgrounnd.setBackgroundColor(alertMessage_level1.getLevelColor());
                    alertMessage_level1.startTime = System.currentTimeMillis();
                    alertMessage_level1.isPlayed = true;
                    alertMessage_level1.level = -1;
                    playSound(alertMessage_level1.getSoundId());

                    if (!mTtsManager.isSpeaking()) {
                        LatLng carLatLng = new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude());
                        RoadInfoHistory roadInfoHistory = RoadInfoHistory.getRoadInfoHistory(carLatLng);
                        if (roadInfoHistory != null) {
                            mHandler.postDelayed(() -> mTtsManager.startSpeaking(alertMessage_level1.getSpeakContent(new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude()))), 300);
                        }else{
                            if(alertMessage_level1.mEventName=="危险车辆"){
                                mHandler.postDelayed(() -> mTtsManager.startSpeaking(alertMessage_level1.getSpeakContentwithoutroadweixian(new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude()),new LatLng(pastTimeCarLatLng.getLatitude(), pastTimeCarLatLng.getLongitude()))), 300);
                            }
                            else{
                            mHandler.postDelayed(() -> mTtsManager.startSpeaking(alertMessage_level1.getSpeakContentwithoutroad(new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude()))), 300);
                        }
                        }
                    }
//                    }, 200);
                } else {
                    mHandler.removeMessages(WHAT_ALERT_MESSAGE_QUEUE);
                    mHandler.sendEmptyMessage(WHAT_ALERT_MESSAGE_QUEUE);
                    mLlAlertMessageTop1.setVisibility(View.INVISIBLE);
//                    outAnimationn(mLlAlertMessageTop1);
                }
            } else {
                mHandler.removeMessages(WHAT_ALERT_MESSAGE_QUEUE);
                mHandler.sendEmptyMessage(WHAT_ALERT_MESSAGE_QUEUE);
                mLlAlertMessageTop1.setVisibility(View.INVISIBLE);
//                outAnimationn(mLlAlertMessageTop1);
            }
        } else {
            mLlAlertMessageRoot.setVisibility(View.INVISIBLE);
        }

        if (remove_level2 != null) {
            if (!remove_level2.isPlayed) {
//                if (mTvAlertMessageTop2.getVisibility() == View.VISIBLE) {
//                    outAnimationn(mTvAlertMessageTop2);
//                }
//                mHandler.postDelayed(() -> {
//                    inAnimationn(mTvAlertMessageTop2);
                mTvAlertMessageTop2.setVisibility(View.VISIBLE);
                mTvAlertMessageTop2.setText(remove_level2.mEventName);
                mTvAlertMessageTop2.setBackgroundColor(remove_level2.getLevelColor());
//                }, 200);
            } else {
                mTvAlertMessageTop2.setVisibility(View.INVISIBLE);
//                outAnimationn(mTvAlertMessageTop2);
            }
        } else {
            mTvAlertMessageTop2.setVisibility(View.INVISIBLE);
//            outAnimationn(mTvAlertMessageTop2);
        }

        if (message_level3 != null) {
            if (!message_level3.isPlayed) {
//                if (mTvAlertMessageTop3.getVisibility() == View.VISIBLE) {
//                    outAnimationn(mTvAlertMessageTop3);
//                }
//                mHandler.postDelayed(() -> {
//                    inAnimationn(mTvAlertMessageTop3);
                mTvAlertMessageTop3.setVisibility(View.VISIBLE);
                mTvAlertMessageTop3.setText(message_level3.mEventName);
                mTvAlertMessageTop3.setBackgroundColor(message_level3.getLevelColor());
//                }, 200);
            } else {
                mTvAlertMessageTop3.setVisibility(View.INVISIBLE);
//                outAnimationn(mTvAlertMessageTop3);
            }
        } else {
            mTvAlertMessageTop3.setVisibility(View.INVISIBLE);
//            outAnimationn(mTvAlertMessageTop3);
        }
    }

    private void outAnimationn(View view) {
        final FadeOutAnimator fadeOutAnimator = new FadeOutAnimator();
        fadeOutAnimator.prepare(view);
        fadeOutAnimator.setDuration(200);
        fadeOutAnimator.start();
        fadeOutAnimator.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                view.setVisibility(View.INVISIBLE);
                fadeOutAnimator.removeAllListener();
            }
        });
    }

    private void inAnimationn(View view) {
        view.setVisibility(View.VISIBLE);
        final FadeInAnimator fadeInAnimator = new FadeInAnimator();
        fadeInAnimator.prepare(view);
        fadeInAnimator.setDuration(400);
        fadeInAnimator.start();
        fadeInAnimator.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                fadeInAnimator.removeAllListener();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSpeakComplate(SpeakComplateEvent event) {
        mHandler.sendEmptyMessageDelayed(WHAT_ALERT_MESSAGE_QUEUE, DELAY_HANDLE_ALERT_MESSAGE);
        Log.v(TAG,"输入01线程");
    }


    private String mOrderId;
    private Overlay mOldOverline;
    private CarLatLngRes lastTimeCarLatLng;
    private CarLatLngRes pastTimeCarLatLng;
    private BitmapDescriptor mCarArrowBitmapDescrption;
    private BitmapDescriptor mCarArrowBitmapDescrptionbig;
    private Overlay mCarOverlay;
    private Overlay startOverlay;
    private Overlay endOverlay;
    private AlertDialog alertDialog;
    private AlertDialog alertDialogOnCar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) { /*
     * savedInstanceState的作用是，当activity被系统回收的时候会调用 onSaveInstate(Bundle savedInstanceState)来保存东西，
     * 那么下一次启动这个Activity的时候会在onCreate的时候保存的东西再传递进来。比如横竖屏切换的时候，会重新走这个onCreate，
     * 如果你在onSaveInstanceState里面存数据的话就可以拿到
     */
        super.onCreate(savedInstanceState);
        intMapFragment();//绑定map，布置map
        getComponent(DataComponent.class).inject(this);

        EventBus.getDefault().register(this);//在此页面注册
        tellMeVehicleId();

//        ArrayList<VehicleIdRes> vehicleIdRes = new ArrayList<>();
//        VehicleIdRes e = new VehicleIdRes();
//        e.setValue("100009");
//        vehicleIdRes.add(e);
//        onVehicleIdGeted(vehicleIdRes);
//
//        (121.22972254,31.33209647),(121.22346433,31.33010155)
//          List<LatLng> parse = RoadLineData.parse(new LatLng(121.22972254, 31.33209647), new LatLng(121.22346433, 31.33010155));
//         Log.d(TAG, "onCreate() called with: savedInstanceState = [" + parsxxe + "]");

        mHandler.sendEmptyMessage(WHAT_UPDATE_CAR_LOCATION);
        mHandler.sendEmptyMessage(WHAT_ALERT_MESSAGE_QUEUE);

        initSounndPool();//播放声音设置

        mPowerManager = ((PowerManager) getContext().getSystemService(Context.POWER_SERVICE));
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getSimpleName());

        if(mVehicleId==null) {
            Log.v(TAG,"mVehicleId"+mVehicleId);
            tellMeVehicleId();//只链一次没有衔接topic成功，再次连接没有得到数据
        }
//下面方法报错是因为oncreat在箭头图像绑定之前
//        CarLatLngRes eCarLatLng = new CarLatLngRes();
//        eCarLatLng.setLongitude(116.404);
//        eCarLatLng.setLatitude(39.945);
//        eCarLatLng.setDirection(0);
//      // onCarPostionChanged(eCarLatLng);


    }

    private void initSounndPool() {
        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sounddata = new HashMap<Integer, Integer>();
        sounddata.put(1, sp.load(getContext(), R.raw.ding1, 1));//1来确定位置音频文件
        sounddata.put(2, sp.load(getContext(), R.raw.ding2, 1));
        sounddata.put(3, sp.load(getContext(), R.raw.ding3, 1));
        sounddata.put(4, sp.load(getContext(), R.raw.ding4, 1));
        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool sound, int sampleId, int status) {
            }
        });
    }

    public void playSound(int sound) {
        AudioManager am = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volumnCurrent = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = volumnCurrent / audioMaxVolumn;

        sp.play(sounddata.get(sound),
                volumnRatio,// 左声道音量
                volumnRatio,// 右声道音量
                1, // 优先级
                0,// 循环播放次数
                1);// 回放速度，该值在0.5-2.0之间 1为正常速度
    }

//用户按下home键返回（creat时也会执行
    @Override
    public void onResume() {
        super.onResume();
        mWakeLock.acquire();
      /* 此处在图像加载之后进行，会导致图像变化
       if(mVehicleId==null) {
            Log.v(TAG,"mVehicleId"+mVehicleId);
            tellMeVehicleId();//只链一次没有衔接topic成功，再次连接没有得到数据
        }*/
        //TODO test函数，设置一些默认数据和后端未写好的接口
        // testAlert();
        /*final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(this.getClass().getSimpleName(), Context.MODE_PRIVATE);
        final String string = sharedPreferences.getString(ROAD_LINE_KEY, "");
        if (!TextUtils.isEmpty(string)) {
            final ArrayList<LatLng> latLngs = new ArrayList<>();
            final org.json.JSONArray jsonArray;
            try {
                jsonArray = new org.json.JSONArray(string);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final org.json.JSONObject jsonObject = jsonArray.optJSONObject(i);
                    final LatLng latLng = new LatLng(jsonObject.optDouble("latitude"), jsonObject.optDouble("longitude"));
                    latLngs.add(latLng);
                }
                //setupDrivingLine(latLngs);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        final String startNode = sharedPreferences.getString(ROAD_LINE_START_KEY, "");
        if (!TextUtils.isEmpty(startNode)) {
            try {
                final org.json.JSONObject jsonObject = new org.json.JSONObject(startNode);
                showStartMarker(jsonObject.optDouble("latitude"), jsonObject.optDouble("longitude"), R.drawable.startpoint);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        final String endNOde = sharedPreferences.getString(ROAD_LINE_END_KEY, "");
        if (!TextUtils.isEmpty(endNOde)) {
            try {
                final org.json.JSONObject jsonObject = new org.json.JSONObject(endNOde);
                showEndMarker(jsonObject.optDouble("latitude"), jsonObject.optDouble("longitude"), R.drawable.endpoint);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final String vehicleId = sharedPreferences.getString(ROAD_LINE_CAR_ID_KEY, "");
        final String orderId = sharedPreferences.getString(ROAD_LINE_ORDER_ID_KEY, "");
        if (TextUtils.isEmpty(this.mVehicleId)) {
            mVehicleId = vehicleId;
            mOrderId = orderId;
        }*/

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        mWakeLock.release();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mHandler.removeMessages(WHAT_UPDATE_CAR_LOCATION);
        mHandler.removeCallbacksAndMessages(null);
        Log.d(TAG, "onDestroy() called");
        super.onDestroy();
      /*  try {
            mMqttManager1.disConnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }*/
    }

    private void tellMeVehicleId() {
        subscribeTtopic(Constants.CLIENT_1, Constants.RECEIVE_TOPIC_VEHICLE_ID);
        final ArrayList<VehicleIdReq> vehicleIdReqs = new ArrayList<>();
        vehicleIdReqs.add(new VehicleIdReq());

        //TODO publish
    //    publishContnet(Constants.CLIENT_1, Constants.SEND_TOPIC_VEHICLE_ID, mGson.toJson(vehicleIdReqs));
//        subscribeTtopic(Constants.CLIENT_2, Constants.RECEIVE_TOPIC_VEHICLE_ID);
//
//        final ArrayList<VehicleIdReq> vehicleIdReqs = new ArrayList<>();
//        vehicleIdReqs.add(new VehicleIdReq());
//        publishContnet(Constants.CLIENT_2, Constants.SEND_TOPIC_VEHICLE_ID, mGson.toJson(vehicleIdReqs));
    }

    private void publishContnet(String clientType, String topic, String content) {
        MqttManager manager = null;
        switch (clientType) {
            case Constants.CLIENT_1:
                manager = mMqttManager1;
                break;
            case Constants.CLIENT_2:
                manager = mMqttManager2;
                break;
            default:
                manager = mMqttManager1;
                break;
        }
        if (manager.isConnect()) {
          //TODO 后端没有对应接口先不publish
           manager.publish(topic, Constants.DEFAULT_MQTT_QOS, content.getBytes());
        } else {
            manager.doConnect();
            if (!manager.isConnect()) {
                Log.v(TAG,"manager:"+manager+"服务连接失败");
                ToastUtils.showShort(String.format("服务连接失败host:[%s],topic:[%s],content:[%s]", manager.getHost(), topic, content));
            }
        }
    }

    private void subscribeTtopic(String clientType, String topic) {
        MqttManager manager = null;
        switch (clientType) {
            case Constants.CLIENT_1:
                manager = mMqttManager1;
                break;
            case Constants.CLIENT_2:
                manager = mMqttManager2;
                break;
            default:
                manager = mMqttManager1;
                break;
        }
//        Log.v("mqtt1","1已连接");
        if (manager.isConnect()) {
            manager.subscribe(topic, Constants.DEFAULT_MQTT_QOS);
            Log.v("MqttManagermqtt1","已连接订阅topic"+ topic.toString());//no
        } else {
            Log.v("MqttManagermqtt1","失去与服务器的链接topic"+ topic.toString()+"正在重连");//执行一次，失去与服务器的链接topictbox/reply/yktype正在重连
            manager.doConnect();
            if (!manager.isConnect()) {
               // ToastUtils.showShort(String.format("失去与服务器的链接topic:[%s]", topic));
                Log.v("MqttManagermqtt1","失去与服务器的链接topic"+ topic.toString());//no
            }
        }
    }

    private void subscribetTopics() {
        subscribeTtopic(Constants.CLIENT_1, Constants.RECEIVE_TOPIC_ROAD_EXCEPITON);
       subscribeTtopic(Constants.CLIENT_1, Constants.RECEIVE_TOPIC_BUS_ROAD);
        subscribeTtopic(Constants.CLIENT_1, Constants.RECEIVE_TOPIC_GANGEROUS_CAR);
        subscribeTtopic(Constants.CLIENT_1, Constants.RECEIVE_TOPIC_ROAD_BARRIERS);
        subscribeTtopic(Constants.CLIENT_1, Constants.RECEIVE_TOPIC_CAR_GPS);
       subscribeTtopic(Constants.CLIENT_1, Constants.RECEIVE_TOPIC_SIGNALLAMP);
        //subscribeTtopic(Constants.CLIENT_2, Constants.RECEIVE_START_DESTIONPOINT);
        subscribeTtopic(Constants.CLIENT_1, Constants.RECEIVE_START_DESTIONPOINT);
        Log.v(TAG,"subscribetTopics启动");//always
    }

    private void intMapFragment() {
        QDSupportMapFragment mapFragment = QDSupportMapFragment.newInstance(18.5f);
        mapFragment.setOnMapViewCreatedCallback(this);
        FragmentManager manager = getChildFragmentManager();
        manager.beginTransaction().add(R.id.fl_home_map, mapFragment, mapFragment.getClass().getName()).commitAllowingStateLoss();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.fragment_home, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();//绑定view，网约车
        setupView();//页面布置
//        initCompass();
    }

    private void initCompass() {
//        mSensorEventListener = new SensorEventListener() {
//            @Override
//            public void onSensorChanged(SensorEvent event) {
//                final float value = event.values[0];
//                mIvCompass.setRotation(value);
//            }
//
//            @Override
//            public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//            }
//        };
//        mSensorManager = ((SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE));
//        mSensorManager.registerListener(mSensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    //eventbus使用
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMqttMessageReceive(MQMessageEvent message) {
        final MqttMessage data = message.getData();
        String jsonStr = new String(data.getPayload());
       jsonStr=jsonStr.replaceAll(": ", ":").replace(" ", "").replace("，", ",");
        Log.v("a1",message.getTopic());//no or always
        if (Constants.RECEIVE_TOPIC_VEHICLE_ID.equals(message.getTopic())) {
            //车机Id
            Log.v("a1","mgson:"+jsonStr);
           final VehicleIdRes vehicleIdRes = mGson.fromJson(jsonStr, VehicleIdRes.class);
//            final ArrayList<VehicleIdRes> vehicleIdRes = mGson.fromJson(jsonStr, new TypeToken<ArrayList<VehicleIdRes>>() {
//            }.getType());
            Log.v("a1","vehicleIdRes:"+vehicleIdRes);
            //开始订阅各个topic
            onVehicleIdGeted(vehicleIdRes);
//            Log.v(TAG,"isUpdateCarLocation:"+isUpdateCarLocation);
////test车辆位置
//       CarLatLngRes eCarLatLng = new CarLatLngRes();
//       eCarLatLng.setLongitude(116.404);
//       eCarLatLng.setLatitude(39.945);
//       eCarLatLng.setDirection(0);
//onCarPostionChanged(eCarLatLng);
        } else if (Constants.RECEIVE_TOPIC_CAR_GPS.equals(message.getTopic())) {
            //车辆gps  121.22972254,31.33209647
            Log.v("a2","mgson:"+jsonStr);
            final CarLatLngRes carLatLng = mGson.fromJson(jsonStr, CarLatLngRes.class);
          //  carLatLng.setDirection(240);//后端传的数据有问题，在这里调整,拐点31.32707829，121.23321171
          /*  if(121.22949932<=carLatLng.getLongitude()&&carLatLng.getLongitude()<=121.23321171&&carLatLng.getLatitude()<=31.32707829){
                carLatLng.setDirection(250);
            }
            else if(carLatLng.getLongitude()<121.22949932&&carLatLng.getLongitude()>=121.22656765){
                carLatLng.setDirection(290);
            }
            else if(carLatLng.getLongitude()<121.22656765){
                carLatLng.setDirection(345);
            }
            else {
                ;
            }*/

            onCarPostionChanged(carLatLng);
        } else if (Constants.RECEIVE_TOPIC_ROAD_EXCEPITON.equals(message.getTopic())) {
            //道路施工 eventType:10  道路打滑eventType:6
            Log.v("a3","mgson:"+jsonStr);
            try {
                final int eventType = new org.json.JSONObject(jsonStr).optInt("eventType");
                if (eventType == 10) {
                    //道路施工
                    final RoadConstructionRes roadExceptionRes = mGson.fromJson(jsonStr, RoadConstructionRes.class);
                    onRoadConstruction(roadExceptionRes);
                } else if (eventType == 6) {
                    //道路打滑
                    final RoadSkidRes roadSkidRes = mGson.fromJson(jsonStr, RoadSkidRes.class);
                    onRoadSkid(roadSkidRes);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (Constants.RECEIVE_TOPIC_ROAD_BARRIERS.equals(message.getTopic())) {
            //路面障碍
            Log.v("a4","mgson:"+jsonStr);
            final RoadBarriersRes roadBarriersRes = mGson.fromJson(jsonStr, RoadBarriersRes.class);
           //  roadBarriersRes.getPosition().setLatitude(31.33575076);//修改传来数据
            onRoadBarriers(roadBarriersRes);
        } else if (Constants.RECEIVE_TOPIC_BUS_ROAD.equals(message.getTopic())) {
            //公交专用车道
            final BusRoadRes busRoadRes = mGson.fromJson(jsonStr, BusRoadRes.class);
            onBusRoad(busRoadRes);
        } else if (Constants.RECEIVE_TOPIC_GANGEROUS_CAR.equals(message.getTopic())) {
            //危险车辆
            Log.v("weixiancheliang","mgson:"+jsonStr);
            final GangerousCarRes gangerousCarRes = mGson.fromJson(jsonStr, GangerousCarRes.class);
            onGangerousCarComming(gangerousCarRes);
        } else if (Constants.RECEIVE_TOPIC_SIGNALLAMP.equals(message.getTopic())) {
            //信号灯
            Log.v("a5xinhaodeng","mgson:"+jsonStr);
            final SignalLampRes signalLampRes = mGson.fromJson(jsonStr, SignalLampRes.class);
            onSignalLampUpdate(signalLampRes);
        } else if (Constants.RECEIVE_START_DESTIONPOINT.equals(message.getTopic())) {
            //线路信息
            Log.v("a6xianlu","mgson:"+jsonStr);
            final StartDestinationRes destinationRes = mGson.fromJson(jsonStr, StartDestinationRes.class);
            getDestinationRes(destinationRes);
        }
    }

    /**
     * 道路施工
     *
     * @param roadExceptionRes
     */
    private void onRoadConstruction(RoadConstructionRes roadExceptionRes) {
        if (lastTimeCarLatLng == null) {
            return;
        }
        for (AlertMessage alertMessage : AlertMessage.fromRowEntity(roadExceptionRes)) {
            LatLng carLatLng = new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude());
            final RoadInfoHistory roadInfoHistory = RoadInfoHistory.getRoadInfoHistory(carLatLng);
            LatLng eventLatLng = new LatLng(alertMessage.mLat, alertMessage.mLon);
            final RoadInfoHistory roadInfoHistory_Alert = RoadInfoHistory.getRoadInfoHistory(eventLatLng);
            if (roadInfoHistory != null && roadInfoHistory_Alert != null) {
//            if (true) {
//                final boolean isNearby = alertMessage.getDistanceToTarget(lastTimeCarLatLng) <= 150;
                final boolean isNearby = alertMessage.getDistanceToTarget(lastTimeCarLatLng) <= mConstraintConfig.getAlertEvent().getDistance().getRoadConstruction();
//                final boolean equalsRoadId = roadInfoHistory.getRoadName().equals(roadInfoHistory_Alert.getRoadName());
                boolean targetIsBefore = false;
                final boolean equalsNextRoadId = RoadLineData.getNextRoadIds(roadInfoHistory.getRoadName()).equalsIgnoreCase(roadInfoHistory_Alert.getRoadName());
                if (equalsNextRoadId) {
                    targetIsBefore = true;
                } else {
                    targetIsBefore = RoadLineData.targetIsBefore(carLatLng, eventLatLng);
                }
                if (equalsNextRoadId || (targetIsBefore && isNearby)) {
//                if (true) {
                    final AlertMessage exists = isExists(alertMessage);//uniqueid判断
                    if (exists != null) {
                        if (!exists.isPlayed) {
                            exists.receiveTime = System.currentTimeMillis();
                        } else {
                            //noThing
                        }
                        return;
                    } else {
                        alertMessage.enqueueTime = System.currentTimeMillis();
                        messageQueue.add(alertMessage);
                    }
                }

            }
            else{//如果没有本地存储道路信息，根据距离来判断
                if(pastTimeCarLatLng!=null) {
                    LatLng pastcarLatLng = new LatLng(pastTimeCarLatLng.getLatitude(), pastTimeCarLatLng.getLongitude());
                    if (DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(eventLatLng)) < 200 && DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(eventLatLng)) < DistanceUtil.getDistance(convertLatLng(pastcarLatLng), convertLatLng(eventLatLng))) {
                        final AlertMessage exists = isExists(alertMessage);
                        if (exists != null) {
                            if (!exists.isPlayed) {
                                exists.receiveTime = System.currentTimeMillis();
                            } else {
                                //noThing
                            }
                            return;
                        } else {
                            alertMessage.enqueueTime = System.currentTimeMillis();
                            // alertMessage.enqueueTime=1540295822815L;
                            messageQueue.add(alertMessage);
                        }
                    }
                }
            }
        }
    }

    /**
     * 道路打滑
     *
     * @param roadSkidRes
     */
    private void onRoadSkid(RoadSkidRes roadSkidRes) {
        if (lastTimeCarLatLng == null) {
            return;
        }
        for (AlertMessage alertMessage : AlertMessage.fromRowEntity(roadSkidRes)) {
            LatLng carLatLng = new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude());
            final RoadInfoHistory roadInfoHistory = RoadInfoHistory.getRoadInfoHistory(carLatLng);
            LatLng eventLatLng = new LatLng(alertMessage.mLat, alertMessage.mLon);
            final RoadInfoHistory roadInfoHistory_Alert = RoadInfoHistory.getRoadInfoHistory(eventLatLng);
            if (roadInfoHistory != null && roadInfoHistory_Alert != null) {
//            if (true) {
//                final boolean equalsRoadId = roadInfoHistory.getRoadName().equalsIgnoreCase(roadInfoHistory_Alert.getRoadName());
                final boolean equalsNextRoadId = RoadLineData.getNextRoadIds(roadInfoHistory.getRoadName()).equalsIgnoreCase(roadInfoHistory_Alert.getRoadName());
//                final boolean isNearby = alertMessage.getDistanceToTarget(lastTimeCarLatLng) <= 150;
                final boolean isNearby = alertMessage.getDistanceToTarget(lastTimeCarLatLng) <= mConstraintConfig.getAlertEvent().getDistance().getRoadSkid();
                boolean targetIsBefore = false;
                if (equalsNextRoadId) {
                    targetIsBefore = true;
                } else {
                    targetIsBefore = RoadLineData.targetIsBefore(carLatLng, eventLatLng);
                }
                if (equalsNextRoadId || (targetIsBefore && isNearby)) {
//                if (true) {
                    final AlertMessage exists = isExists(alertMessage);
                    if (exists != null) {
                        if (!exists.isPlayed) {
                            exists.receiveTime = System.currentTimeMillis();
                        }else{
                            //noThing
                        }
                        return;
                    } else {
                        alertMessage.enqueueTime = System.currentTimeMillis();
                        messageQueue.add(alertMessage);
                    }
                }
            }
            else{//如果没有本地存储道路信息，根据距离来判断
                if(pastTimeCarLatLng!=null) {
                    LatLng pastcarLatLng = new LatLng(pastTimeCarLatLng.getLatitude(), pastTimeCarLatLng.getLongitude());
                    if (DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(eventLatLng)) < 200 && DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(eventLatLng)) < DistanceUtil.getDistance(convertLatLng(pastcarLatLng), convertLatLng(eventLatLng))) {
                        final AlertMessage exists = isExists(alertMessage);
                        if (exists != null) {
                            if (!exists.isPlayed) {
                                exists.receiveTime = System.currentTimeMillis();
                            } else {
                                //noThing
                            }
                            return;
                        } else {
                            alertMessage.enqueueTime = System.currentTimeMillis();
                            // alertMessage.enqueueTime=1540295822815L;
                            messageQueue.add(alertMessage);
                        }
                    }
                }
            }
        }
    }

    /**
     * 路面障碍
     *
     * @param roadExceptionRes
     */
    private void onRoadBarriers(RoadBarriersRes roadExceptionRes) {
        //当前接收到的车辆位置信息
        if (lastTimeCarLatLng == null) {
            Log.v("a4"," onRoadBarriers:lastTimeCarLatLng == null");
            return;
        }
        for (AlertMessage alertMessage : AlertMessage.fromRowEntity(roadExceptionRes)) {
            LatLng carLatLng = new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude());
            final RoadInfoHistory roadInfoHistory = RoadInfoHistory.getRoadInfoHistory(carLatLng);
            LatLng eventLatLng = new LatLng(alertMessage.mLat, alertMessage.mLon);
            final RoadInfoHistory roadInfoHistory_Alert = RoadInfoHistory.getRoadInfoHistory(eventLatLng);
            Log.v("a4","carLatLng"+carLatLng.toString());
            Log.v("a4", "eventLatLng"+eventLatLng.toString());
            if (roadInfoHistory_Alert != null && roadInfoHistory != null) {
                Log.v(TAG,"roadInfoHistory_Alert:"+roadInfoHistory_Alert.getRoadName()+" :"+roadInfoHistory.getRoadName());
                //            if (true) {
               final boolean equalsNextRoadId = roadInfoHistory.getRoadName().equals(roadInfoHistory_Alert.getRoadName());
             //   final boolean equalsNextRoadId =true;
                //  final boolean equalsNextRoadId = RoadLineData.getNextRoadIds(roadInfoHistory.getRoadName()).equalsIgnoreCase(roadInfoHistory_Alert.getRoadName());
//                final boolean distanceToTarget = alertMessage.getDistanceToTarget(lastTimeCarLatLng) <= 150;
                final boolean distanceToTarget = alertMessage.getDistanceToTarget(lastTimeCarLatLng) <= mConstraintConfig.getAlertEvent().getDistance().getRoadObstructions();
               boolean targetIsBefore = false;
                if (equalsNextRoadId) {
                    targetIsBefore = true;
                } else {
                    Log.v(TAG,"道路不相同");
                    targetIsBefore = RoadLineData.targetIsBefore(carLatLng, eventLatLng);
                }
                if (equalsNextRoadId || (targetIsBefore && distanceToTarget)) {
//                if (true) {
                    final AlertMessage exists = isExists(alertMessage);
                    if (exists != null) {
                        if (!exists.isPlayed) {
                            exists.receiveTime = System.currentTimeMillis();
                        } else {
                            //noThing
                        }
                        return;
                    } else {
                        Log.v(TAG,"exists null");
                      alertMessage.enqueueTime = System.currentTimeMillis();
                       // alertMessage.enqueueTime=1540295822815L;
                        messageQueue.add(alertMessage);
                    }
                }

            }
            else{//如果没有本地存储道路信息，根据距离来判断
                if(pastTimeCarLatLng!=null) {
                    LatLng pastcarLatLng = new LatLng(pastTimeCarLatLng.getLatitude(), pastTimeCarLatLng.getLongitude());
                    if (DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(eventLatLng)) < 200 && DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(eventLatLng)) < DistanceUtil.getDistance(convertLatLng(pastcarLatLng), convertLatLng(eventLatLng))) {
                        final AlertMessage exists = isExists(alertMessage);
                        if (exists != null) {
                            if (!exists.isPlayed) {
                                exists.receiveTime = System.currentTimeMillis();
                            } else {
                                //noThing
                            }
                            return;
                        } else {
                            Log.v(TAG,"exists null");
                            alertMessage.enqueueTime = System.currentTimeMillis();
                            // alertMessage.enqueueTime=1540295822815L;
                            messageQueue.add(alertMessage);
                        }
                    }
                }
            }
        }
    }

    /**
     * 公交专用车道
     *
     * @param busRoadRes
     */
    private void onBusRoad(BusRoadRes busRoadRes) {
        if (lastTimeCarLatLng == null) {
            return;
        }
        for (AlertMessage alertMessage : AlertMessage.fromRowEntity(busRoadRes)) {
            final LatLng carLatLng = new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude());
            final RoadInfoHistory roadInfoHistory = RoadInfoHistory.getRoadInfoHistory(carLatLng);
            LatLng eventLatLng = new LatLng(alertMessage.mLat, alertMessage.mLon);
            final RoadInfoHistory roadInfoHistory_Alert = RoadInfoHistory.getRoadInfoHistory(eventLatLng);
            //距离公交车道小于5
            if (roadInfoHistory != null && roadInfoHistory_Alert != null) {
//                final boolean disLess5 = alertMessage.getDistanceToTarget(lastTimeCarLatLng) <= 100;
                final boolean disLess5 = alertMessage.getDistanceToTarget(lastTimeCarLatLng) <= mConstraintConfig.getAlertEvent().getDistance().getBusLanes();
//                final boolean equalsRoadId = roadInfoHistory.getRoadName().equals(roadInfoHistory_Alert.getRoadName());
                final boolean equalsNextRoadId = RoadLineData.getNextRoadIds(roadInfoHistory.getRoadName()).equalsIgnoreCase(roadInfoHistory_Alert.getRoadName());
                boolean targetIsBefore = false;
                if (equalsNextRoadId) {
                    targetIsBefore = true;
                } else {
                    targetIsBefore = RoadLineData.targetIsBefore(carLatLng, eventLatLng);
                }
                if (equalsNextRoadId || (targetIsBefore && disLess5)) {
//                if (true) {
                    final AlertMessage exists = isExists(alertMessage);
                    if (exists != null) {
                        if (!exists.isPlayed) {
                            exists.receiveTime = System.currentTimeMillis();
                        } else {
                            //noThing
                        }
                        return;
                    } else {
                        alertMessage.enqueueTime = System.currentTimeMillis();
                        messageQueue.add(alertMessage);
                    }
                }
            }
            else{//如果没有本地存储道路信息，根据距离来判断
                if(pastTimeCarLatLng!=null) {
                    LatLng pastcarLatLng = new LatLng(pastTimeCarLatLng.getLatitude(), pastTimeCarLatLng.getLongitude());
                    if (DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(eventLatLng)) < mConstraintConfig.getAlertEvent().getDistance().getBusLanes() && DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(eventLatLng)) < DistanceUtil.getDistance(convertLatLng(pastcarLatLng), convertLatLng(eventLatLng))) {
                        final AlertMessage exists = isExists(alertMessage);
                        if (exists != null) {
                            if (!exists.isPlayed) {
                                exists.receiveTime = System.currentTimeMillis();
                            } else {
                                //noThing
                            }
                            return;
                        } else {
                            alertMessage.enqueueTime = System.currentTimeMillis();
                            // alertMessage.enqueueTime=1540295822815L;
                            messageQueue.add(alertMessage);
                        }
                    }
                }
            }
        }
    }

    /**
     * 危险车辆
     *
     * @param gangerousCarRes
     */
    private void onGangerousCarComming(GangerousCarRes gangerousCarRes) {
        if (lastTimeCarLatLng == null) {
            return;
        }
        for (AlertMessage alertMessage : AlertMessage.fromRowEntity(gangerousCarRes)) {
            if (!TextUtils.isEmpty(alertMessage.mUniqueId) && alertMessage.mUniqueId.equals(this.mVehicleId)) {
                continue;
            }
            final LatLng carLocation = new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude());
            final RoadInfoHistory roadInfoHistory = RoadInfoHistory.getRoadInfoHistory(carLocation);
//            if (roadInfoHistory == null) return;

            LatLng eventLocation = new LatLng(alertMessage.mLat, alertMessage.mLon);
            final RoadInfoHistory roadInfoHistory_Alert = RoadInfoHistory.getRoadInfoHistory(eventLocation);
//            if (roadInfoHistory_Alert == null) return;
            if (roadInfoHistory_Alert != null && roadInfoHistory != null) {
                final int roadLine = roadInfoHistory.getRoadLine(carLocation);
                final int roadLine1 = roadInfoHistory_Alert.getRoadLine(eventLocation);
                Log.v("weixianche","buweinull");
                if (roadLine == roadLine1) {
//            if (true) {
                    if (!TextUtils.isEmpty(alertMessage.extra)) {
                        final String[] split = alertMessage.extra.split(",");
                        for (String s : split) {
                            try {//userSpeed <=2 超速请您向  userBreake <= 2急刹车倾向 userCutin<=2及切入倾向 userinfraction <= 2 非法驾驶
                                final int i = Integer.parseInt(s.trim());
                                if (i <= 2 && i >= 0) {
                                    final AlertMessage exists = isExists(alertMessage);
                                    if (exists != null) {
                                        if (!exists.isPlayed) {
                                            exists.receiveTime = System.currentTimeMillis();
                                            exists.mLat = alertMessage.mLat;
                                            exists.mLon = alertMessage.mLon;
                                        } else {
                                            //noThing
                                        }
                                        return;
                                    } else {
                                        alertMessage.enqueueTime = System.currentTimeMillis();
                                        messageQueue.add(alertMessage);
                                    }
                                }
                            } catch (Exception e) {

                            }
                        }
                    }
                    //相同车道
                }
            }
            else{
                LatLng pastcarLatLng = new LatLng(pastTimeCarLatLng.getLatitude(), pastTimeCarLatLng.getLongitude());
                if (DistanceUtil.getDistance(convertLatLng(carLocation), convertLatLng(eventLocation)) < 30 ) {
                    Log.v("weixianche","危险车辆开始执行");
                    if (!TextUtils.isEmpty(alertMessage.extra)) {
                        final String[] split = alertMessage.extra.split(",");
                        for (String s : split) {
                            try {//userSpeed <=2 超速请您向  userBreake <= 2急刹车倾向 userCutin<=2及切入倾向 userinfraction <= 2 非法驾驶
                                final int i = Integer.parseInt(s.trim());
                                if (i <= 2 && i >= 0) {
                                    final AlertMessage exists = isExists(alertMessage);
                                    if (exists != null) {
                                        Log.v("weixianche","exists != null"+alertMessage.mUniqueId);
                                        if (!exists.isPlayed) {
                                            Log.v("weixianche","!exists.isPlayed"+alertMessage.mUniqueId);
                                            exists.receiveTime = System.currentTimeMillis();
                                            exists.mLat = alertMessage.mLat;
                                            exists.mLon = alertMessage.mLon;
                                        } else {
                                            Log.v("weixianche","exists.isPlayed"+alertMessage.mUniqueId);
                                            //noThing
                                        }
                                        return;
                                    } else {
                                        Log.v("weixianche","exists = null"+alertMessage.mUniqueId);
                                        alertMessage.enqueueTime = System.currentTimeMillis();
                                        messageQueue.add(alertMessage);
                                    }
                                }
                            } catch (Exception e) {
                                Log.v("weixianche",e.toString());
                            }
                        }
                    }
                }

            }
        }

    }

    //返回message中和alertMessage一样的
    private AlertMessage isExists(AlertMessage alertMessage) {
        for (AlertMessage message : messageQueue) {
            if (!TextUtils.isEmpty(message.mUniqueId)) {//uid不为空
                if (message.mUniqueId.equalsIgnoreCase(alertMessage.mUniqueId)) {
                    return message;
                }
            }
        }
        return null;
    }


    /**
     * 获取到 vehicleId
     *
     * @param vehicleIdRes
     */
//   private void onVehicleIdGeted(List<VehicleIdRes> vehicleIdRes) {
//        Log.v("vid", "条件不符合");
//        if (vehicleIdRes != null && vehicleIdRes.size() > 0 && !TextUtils.isEmpty(vehicleIdRes.get(0).getValue())) {
//            final String vehicleId = vehicleIdRes.get(0).getValue();
//            Log.v("vid", "onVehicleIdGeted() called with: vehicleIdRes = [" + vehicleId + "]");
//            HomeFragment.this.mVehicleId = vehicleId;
//            fixTopic(vehicleId);
//            subscribetTopics();
//           //shortToast(String.format("VehicleId获取成功%s", mVehicleId));
//        }
//    }
    private void onVehicleIdGeted(VehicleIdRes vehicleIdRes) {
        if (vehicleIdRes != null  && !TextUtils.isEmpty(vehicleIdRes.getValue())) {
            final String vehicleId = vehicleIdRes.getValue();
            Log.v("vid", "onVehicleIdGeted() called with: vehicleIdRes = [" + vehicleId + "]");
            HomeFragment.this.mVehicleId = vehicleId;
            fixTopic(vehicleId);
            subscribetTopics();
            //shortToast(String.format("VehicleId获取成功%s", mVehicleId));
        }
    }

    /**
     * 获取目的地信息
     *
     * @param destinationRes
     */
    private void getDestinationRes(StartDestinationRes destinationRes) {
        Log.v(TAG,"destinationRes=null");
        if (destinationRes != null) {
            this.mOrderId = destinationRes.getOrderId() + "";
         Log.v(TAG,"destinationRes != null");
            double startLon = destinationRes.getStartLon();
            double startLat = destinationRes.getStartLat();
            double endLon = destinationRes.getEndLon();
            double endLat = destinationRes.getEndLat();
            BaidusetupDrivingLine(startLat,startLon,endLat,endLon);
          //  showStartMarker(startLat, startLon, R.drawable.startpoint);
            //showEndMarker(endLat, endLon, R.drawable.endpoint);
         //*latlngs.*//*

         /*  本地只保存了上海的数据，如用北京数据则需要注释这一段
          List<LatLng> rawLatLngs = RoadLineData.findLineByBeginEndLatLng(new LatLng(destinationRes.getStartLat(), destinationRes.getStartLon()), new LatLng(destinationRes.getEndLat(), destinationRes.getEndLon()));
            final List<LatLng> baiduLatLngs = RoadLineData.convertToBaiduLatLons(rawLatLngs);
            //绘制线路
            // setupDrivingLine(baiduLatLngs);

             //存储 数据
            final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(this.getClass().getSimpleName(), Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(ROAD_LINE_KEY, mGson.toJson(baiduLatLngs)).apply();
            sharedPreferences.edit().putString(ROAD_LINE_START_KEY, mGson.toJson(new LatLng(startLat, startLon))).apply();
            sharedPreferences.edit().putString(ROAD_LINE_END_KEY, mGson.toJson(new LatLng(endLat, endLon))).apply();
            sharedPreferences.edit().putString(ROAD_LINE_CAR_ID_KEY, this.mVehicleId).apply();
            sharedPreferences.edit().putString(ROAD_LINE_ORDER_ID_KEY, this.mOrderId).apply();
            //上报数据
            snedRouteLineDataToServer(rawLatLngs);*/

        }
    }



    private static String ROAD_LINE_KEY = "roadLines";
    private static String ROAD_LINE_START_KEY = "startRoadLines";
    private static String ROAD_LINE_END_KEY = "endRoadLines";
    private static String ROAD_LINE_CAR_ID_KEY = "carIdRoadLines";
    private static String ROAD_LINE_ORDER_ID_KEY = "OrderIdRoadLines";

    private void snedRouteLineDataToServer(List<LatLng> parse) {
        final ArrayList<RouteLineDataReq.LatLng> latLngs = new ArrayList<>();
        for (LatLng latLng : parse) {
            latLngs.add(new RouteLineDataReq.LatLng(latLng.latitude, latLng.longitude));
        }
        final RouteLineDataReq routeLineDataReq = new RouteLineDataReq(this.mVehicleId, this.mOrderId, latLngs);
     //  publishContnet(Constants.CLIENT_2, Constants.SEND_MESSAGE_TOSERVER, JSON.toJSONString(routeLineDataReq));
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{")
                .append("\"orderId\":" + this.mOrderId)
                .append(",")
                .append("\"vehicleId\":" + this.mVehicleId)
                .append(",")
                .append("\"data\":" + mGson.toJson(latLngs))
                .append("}");
        //TODO 2号端口未开，先用1
      // publishContnet(Constants.CLIENT_2, Constants.SEND_MESSAGE_TOSERVER, stringBuilder.toString());
     //   publishContnet(Constants.CLIENT_1, Constants.SEND_MESSAGE_TOSERVER, stringBuilder.toString());
    }


    /**
     * 信号灯
     *
     * @param signalLampRes
     */
    private void onSignalLampUpdate(SignalLampRes signalLampRes) {
        //double gpsLong = 121.2234367, gpsLat = 31.3300122;
//        double gpsLong = 121.2300554, gpsLat = 31.3259353;
//        lastTimeCarLatLng = new CarLatLngRes();
//        lastTimeCarLatLng.setLongitude(121.2300554);
//        lastTimeCarLatLng.setLatitude(31.3259353);
        lastReceiveSignalLampTime = System.currentTimeMillis();
        if (lastTimeCarLatLng != null) {
            //更具车辆经纬度查 roadInfo 不为空则查找成功
            Log.v(TAG,"lastTimeCarLatLng != null");
            RoadInfoHistory carRoadInfoHistory = RoadInfoHistory.getRoadInfoHistory(new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude()));

            if (carRoadInfoHistory != null) {
                Log.v(TAG,"carRoadInfoHistory != null");
                //更具roadId查本地路灯的经纬度 不为空则查找成功
                SignalGpsMode signalGpsMode = getSignalLampStartNodeId(carRoadInfoHistory.getRoadName());
                if (signalGpsMode != null) {
                    Log.v(TAG,"signalGpsMode != null");
                    final double distance = DistanceUtil.getDistance(convertLatLng(new LatLng(signalGpsMode.lat, signalGpsMode.lng)), convertLatLng(new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude())));
                    Log.v(TAG,"distance:"+distance);//400mi
//                     final long l = System.currentTimeMillis() - signalLampRes.getTimestamp();
//                    Log.d("FenngGe", "onSignalLampUpdate:  " + l);
                   if (distance > 500) {
 //                  if (distance > mConstraintConfig.getSignalLamp().getDistance()) {
                        Log.v(TAG,"distance > mConstraintConfig.getSignalLamp().getDistance()");
                        hideSignalLampViews();
                        return;
                    }
                    for (SignalLampRes.EntrancesBean entrancesBean : signalLampRes.getEntrances()) {
                        final List<SignalLampRes.EntrancesBean.RoadBeanX.RoadBean> road = entrancesBean.getRoad().getRoad();
                        Log.v(TAG,"road:"+road);
                        for (SignalLampRes.EntrancesBean.RoadBeanX.RoadBean roadBean : road) {
                            final String startNode = roadBean.getStartNode();
                            Log.v(TAG,"startNode:"+startNode+" "+"signalGpsMode.startNodeId:"+signalGpsMode.startNodeId);//startNode:3_1_1 signalGpsMode.startNodeId:1_9_5
                          // if (startNode.equals(signalGpsMode.startNodeId)) {
                            //TODO 应当使用上方逻辑，但是后端3_1_1在本地无对应，使用下方逻辑，自己定点
                               if (signalGpsMode.startNodeId.equals("1_5_1")) {

                       //         Log.v(TAG,"startNode.equals(signalGpsMode.startNodeId==true");
                               final String startNodeId = signalGpsMode.startNodeId;
                                 if (!startNodeId.equals(mShowingSignalLampStartNodeId)) {
                                    Log.v(TAG,"!startNodeId.equals(mShowingSignalLampStartNodeId");
                                    hideSignalLampViews();
                                }
                                mShowingSignalLampStartNodeId = startNodeId;
                                final long timeMillis = System.currentTimeMillis();
                                for (SignalLampRes.EntrancesBean.ExitsBean exitsBean : entrancesBean.getExits()) {
//                                    exitsBean = offsetPhase(timeMillis - signalLampRes.getTimestamp(), exitsBean);
                                    updateSignalLampStatus(signalGpsMode, exitsBean);
                                }
                                return;
                          }
                        }
                    }
                } else {
                    hideSignalLampViews();
                }
            } else {
                //carRoadInfoHistory = null
                //对应红绿灯坐标在signalGpsinfo中查询，3_1_1对应下面的经纬度
//                double distance1 = DistanceUtil.getDistance(convertLatLng(new LatLng(39.788840799,116.515208944)), convertLatLng(new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude())));
//                double distance2 = DistanceUtil.getDistance(convertLatLng(new LatLng(39.788840799,116.515208944)), convertLatLng(new LatLng(pastTimeCarLatLng.getLatitude(), pastTimeCarLatLng.getLongitude())));
                double distance1 = DistanceUtil.getDistance(convertLatLng(new LatLng(39.7854586512,116.49132221)), convertLatLng(new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude())));
                double distance2 = DistanceUtil.getDistance(convertLatLng(new LatLng(39.7854586512,116.49132221)), convertLatLng(new LatLng(pastTimeCarLatLng.getLatitude(), pastTimeCarLatLng.getLongitude())));
             //   Log.v("msg","carRoadInfoHistory = null"+distance1+"  "+ distance2 );
                if(distance1<=500&&distance1<=distance2) {
           //         Log.v("msg","distance1<500&&distance1<distance2");
                    for (SignalLampRes.EntrancesBean entrancesBean : signalLampRes.getEntrances()) {
                            for (SignalLampRes.EntrancesBean.ExitsBean exitsBean : entrancesBean.getExits()) {
//                                    exitsBean = offsetPhase(timeMillis - signalLampRes.getTimestamp(), exitsBean);
                                SignalGpsMode signalGpsMode = null;
                                updateSignalLampStatus(signalGpsMode, exitsBean);
                            }
                        }

                }else {
                    hideSignalLampViews();
                }
            }
        } else {
            hideSignalLampViews();
        }
    }

    //        2红灯 3 黄灯 4绿灯
    private SignalLampRes.EntrancesBean.ExitsBean offsetPhase(long millisDiff, SignalLampRes.EntrancesBean.ExitsBean exitsBean) {
        final SignalLampRes.EntrancesBean.ExitsBean result = new SignalLampRes.EntrancesBean.ExitsBean();
        final long l = millisDiff/* - exitsBean.getCountdown()*/;
        final SignalLampRes.EntrancesBean.ExitsBean.PhaseBean phase = exitsBean.getPhase();
        if (phase == null) {
            return exitsBean;
        }
        final long l1 = l % (phase.getGREEN() + phase.getYELLOW() + phase.getRED());
        if (l1 < phase.getGREEN()) {
            result.setCurrentStatus(4);
//            result.setCurrentStatus(exitsBean.getCurrentStatus());
            result.setCountdown((phase.getGREEN() - (int) l1));
//            result.setCountdown(exitsBean.getCountdown());
            result.setExitDirection(exitsBean.getExitDirection());
            result.setPhase(exitsBean.getPhase());
        } else if (l1 < (phase.getGREEN() + phase.getYELLOW())) {
            result.setCurrentStatus(3);
//            result.setCurrentStatus(exitsBean.getCurrentStatus());
            result.setCountdown((phase.getGREEN() + phase.getYELLOW()) - (int) l1);
//            result.setCountdown(exitsBean.getCountdown());
            result.setExitDirection(exitsBean.getExitDirection());
            result.setPhase(exitsBean.getPhase());
        } else if (l1 < phase.getGREEN() + phase.getYELLOW() + phase.getRED()) {
            result.setCurrentStatus(2);
//            result.setCurrentStatus(exitsBean.getCurrentStatus());
            result.setCountdown((phase.getGREEN() + phase.getYELLOW() + phase.getRED()) - (int) l1);
//            result.setCountdown(exitsBean.getCountdown());
            result.setExitDirection(exitsBean.getExitDirection());
            result.setPhase(exitsBean.getPhase());
        }
        return result;
    }

    //TODO 是否隐藏信号灯，下方注释为接受不到信号时隐藏信号灯
    private void hideSignalLampViews() {
        mSlvDown.setVisibility(View.INVISIBLE);
        mSlvLeft.setVisibility(View.INVISIBLE);
        mSlvTop.setVisibility(View.INVISIBLE);
        mSlvRight.setVisibility(View.INVISIBLE);
        mShowingSignalLampStartNodeId = "";
    }

    private void updateSignalLampStatus(SignalGpsMode signalGpsMode, SignalLampRes.EntrancesBean.ExitsBean exitsBean) {
        final int countdown = exitsBean.getCountdown();//时间数字
        final int currentStatus = exitsBean.getCurrentStatus();//颜色：1，不处理，2，红，3，黄，4，绿
        final int exitDirection = exitsBean.getExitDirection();//红绿灯种类：1,掉头，2，左，3，直行，4，右

//        2红灯 3 黄灯 4绿灯
        switch (exitDirection) {
            case 1://掉头
                mSlvDown.setVisibility(View.VISIBLE);
                mSlvDown.updateStatus(SignalLampView.DIRECTION_DOWN, countdown + "", getSignalLampColor(currentStatus));
                break;
            case 2://左
                mSlvLeft.setVisibility(View.VISIBLE);
                mSlvLeft.updateStatus(SignalLampView.DIRECTION_LEFT, countdown + "", getSignalLampColor(currentStatus));
                break;
            case 3://上
                mSlvTop.setVisibility(View.VISIBLE);
                mSlvTop.updateStatus(SignalLampView.DIRECTION_TOP, countdown + "", getSignalLampColor(currentStatus));
                break;
            case 4://右
                mSlvRight.setVisibility(View.VISIBLE);
                Log.v(TAG,"right 红绿灯");
                mSlvRight.updateStatus(SignalLampView.DIRECTION_RIGHT, countdown + "", getSignalLampColor(currentStatus));
                break;
        }
    }

    private int getSignalLampColor(int currentStatus) {
//        2红灯 3 黄灯 4绿灯
        switch (currentStatus) {
            case 1://不处理
                break;
            case 2:
                return SignalLampView.LIGHT_MODE_RED;
            case 3:
                return SignalLampView.LIGHT_MODE_YELLOW;
            case 4:
                return SignalLampView.LIGHT_MODE_GREEN;
        }
        return SignalLampView.LIGHT_MODE_RED;
    }

    private List<SignalGpsMode> handleSignalGps(List<String[]> strings) {
        final ArrayList<SignalGpsMode> signalGpsModes = new ArrayList<>();
        for (String[] string : strings) {
            final SignalGpsMode signalGpsMode = SignalGpsMode.parseInfo(string);
            signalGpsModes.add(signalGpsMode);
        }
        return signalGpsModes;
    }

    private SignalGpsMode getSignalLampStartNodeId(String roadId) {
        roadId = roadId.toUpperCase();
        final List<SignalGpsMode> signalGpsModes = handleSignalGps(signGpsArray);
        for (SignalGpsMode signalGpsMode : signalGpsModes) {
            if (roadId.equals(signalGpsMode.roadId)) {
                return signalGpsMode;
            }
        }
        return null;
    }


    /**
     * 车辆位置发生变化
     *当传递message为xxx时调用
     *
     * @param carLatLng
     */
    private void onCarPostionChanged(CarLatLngRes carLatLng) {
        if (!isUpdateCarLocation) {
           Log.v(TAG,"GPS jieshu");
            return;
        }
        if( this.lastTimeCarLatLng!=null){
            this.pastTimeCarLatLng=this.lastTimeCarLatLng;
        }

        Log.v(TAG,"GPS kasihi");
        double carLongtitude = carLatLng.getLongitude();
        double carLatutide = carLatLng.getLatitude();
        LatLng latLng = new LatLng(carLatutide, carLongtitude);
        this.lastTimeCarLatLng = carLatLng;
        setupCarLocation(mBaiduMap, latLng);
        isUpdateCarLocation = false;
    }

    private void fixTopic(String vehicleId) {
        Constants.RECEIVE_TOPIC_ROAD_EXCEPITON = String.format(Constants.RECEIVE_TOPIC_ROAD_EXCEPITON, vehicleId);
        Constants.RECEIVE_TOPIC_ROAD_BARRIERS = String.format(Constants.RECEIVE_TOPIC_ROAD_BARRIERS, vehicleId);
        Constants.RECEIVE_TOPIC_GANGEROUS_CAR = String.format(Constants.RECEIVE_TOPIC_GANGEROUS_CAR, vehicleId);
        Constants.RECEIVE_START_DESTIONPOINT = String.format(Constants.RECEIVE_START_DESTIONPOINT, vehicleId);
        Constants.RECEIVE_TOPIC_SIGNALLAMP = String.format(Constants.RECEIVE_TOPIC_SIGNALLAMP, vehicleId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
//        mSensorManager.unregisterListener(mSensorEventListener);
    }

    private void initView() {
        final View contentView = getView();
//        contentView.findViewById(R.id.ib_home_relocation).setOnClickListener((View view1) -> {
//            reLocation();
//        });

        mLlOnOrOffCar = contentView.findViewById(R.id.ll_event_onOrOffCar);
        mLlAlertMessageRoot = contentView.findViewById(R.id.ll_alertmessage_root);
        mLlAlertMessageTop1 = contentView.findViewById(R.id.ll_alertmessage_top1);
        mllAlertMessageTop1Backgrounnd = contentView.findViewById(R.id.ll_alertMessage_topBackground);
        mTvTop1LevelName = contentView.findViewById(R.id.tv_alertMessage_level);
        mTvTop1Name = contentView.findViewById(R.id.tv_alertMessage_name);
        mIvMessageIcon = contentView.findViewById(R.id.iv_alertMessage_iconn);
        mTvAlertMessageTop2 = contentView.findViewById(R.id.tv_alertmessage_top2);
        mTvAlertMessageTop3 = contentView.findViewById(R.id.tv_alertmessage_top3);
        mRgModes = contentView.findViewById(R.id.rg_mode_modes);
        mIvCompass = contentView.findViewById(R.id.iv_mode_compass);
        mRvEventList = contentView.findViewById(R.id.rv_event_eventlist);
        mLlSignalLampWrapper = contentView.findViewById(R.id.ll_home_signalLampWrapper);
        mSlvDown = contentView.findViewById(R.id.slv_home_signalLamp1);
        mSlvLeft = contentView.findViewById(R.id.slv_home_signalLamp2);
        mSlvTop = contentView.findViewById(R.id.slv_home_signalLamp3);
        mSlvRight = contentView.findViewById(R.id.slv_home_signalLamp4);

        mTboxStatus = contentView.findViewById(R.id.tv_debugInfo_tboxStatus);
        mCloudStatus = contentView.findViewById(R.id.tv_debugInfo_cloudStatus);
        mTvCurrentVehicleId = contentView.findViewById(R.id.tv_debugInfo_currentVehicleId);
        mTvCurrentOrderId = contentView.findViewById(R.id.tv_debugINfo_currentOrderId);
        mTvCurrentRoadId = contentView.findViewById(R.id.tv_debugINfo_currentRoadId);
        mTvCurrentVersion = contentView.findViewById(R.id.tv_debugINfo_currentVersion);
        hideSignalLampViews();

        contentView.findViewById(R.id.ll_event_onCar).setOnClickListener((View view) -> {
            if (TextUtils.isEmpty(this.mOrderId)) {
                shortToast("非网约车路线,无法上车!");
                return;
            }
            if (TextUtils.isEmpty(this.mVehicleId)) {
                shortToast("当前没有获取到VehicleId");
                return;
            }
            if (alertDialogOnCar == null) {
                alertDialogOnCar = new AlertDialog.Builder(getActivity())
                        .setTitle("系统提示")
                        .setMessage("确定上车？")
                        .setPositiveButton("确定", (DialogInterface dialog, int which) -> {
                            onOnCarClick();
                        }).setNegativeButton("取消", (DialogInterface dialog, int which) -> {
                            alertDialogOnCar.dismiss();
                        }).create();
            }
            alertDialogOnCar.show();
        });

        contentView.findViewById(R.id.ll_event_offCar).setOnClickListener((View view) -> {
            if (TextUtils.isEmpty(this.mOrderId)) {
                shortToast("非网约车路线,无法下车!");
                return;
            }
            if (TextUtils.isEmpty(this.mVehicleId)) {
                shortToast("当前没有获取到VehicleId");
                return;
            }
            if (alertDialog == null) {
                alertDialog = new AlertDialog.Builder(getActivity())
                        .setTitle("系统提示")
                        .setMessage("确定下车？")
                        .setPositiveButton("确定", (DialogInterface dialog, int which) -> {
                            onOffCarClick();
                        }).setNegativeButton("取消", (DialogInterface dialog, int which) -> {
                            alertDialog.dismiss();
                        }).create();
            }
            alertDialog.show();
        });

    }

    private void onOnCarClick() {
        OnCarReq onCarReq = new OnCarReq();
        onCarReq.setOrderId(Integer.parseInt(this.mOrderId));
        onCarReq.setVehicleId(Integer.parseInt(this.mVehicleId));
        publishContnet(Constants.CLIENT_2, Constants.SEND_TOPIC_ONCAR, mGson.toJson(onCarReq));
        shortToast("上车成功");
    }

    private void onOffCarClick() {
        OffCarReq offCarReq = new OffCarReq();
        offCarReq.setOrderId(Integer.parseInt(this.mOrderId));
        offCarReq.setVehicleId(Integer.parseInt(this.mVehicleId));
        publishContnet(Constants.CLIENT_2, Constants.SEND_TOPIC_OFFCAR, mGson.toJson(offCarReq));
        shortToast("下车成功");
      /*  if (mOldOverline != null) {
            mOldOverline.remove();
            mOldOverline = null;

        }
        if (startOverlay != null) {
            startOverlay.remove();
            startOverlay = null;
        }
        if (endOverlay != null) {
            endOverlay.remove();
            endOverlay = null;
        }*/

        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences(this.getClass().getSimpleName(), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(ROAD_LINE_KEY, "").commit();
        sharedPreferences.edit().remove(ROAD_LINE_KEY).commit();
        sharedPreferences.edit().remove(ROAD_LINE_START_KEY).commit();
        sharedPreferences.edit().remove(ROAD_LINE_END_KEY).commit();
        sharedPreferences.edit().remove(ROAD_LINE_CAR_ID_KEY).commit();
        sharedPreferences.edit().remove(ROAD_LINE_ORDER_ID_KEY).commit();

        final String vehicleId = sharedPreferences.getString(ROAD_LINE_CAR_ID_KEY, "");
        final String orderId = sharedPreferences.getString(ROAD_LINE_ORDER_ID_KEY, "");
        if (TextUtils.isEmpty(this.mVehicleId)) {
            mVehicleId = vehicleId;
            mOrderId = orderId;
        }

    }


    private void setupView() {
        reLayoutAlertMessagePanel();//弹出警告位置布置
        reLayoutSignalLampPanel();//应该是布置按钮布局
        initModePanel();//按钮事件
        initEventListPanel();//布置仿真view，上报仿真信息
    }


    private static class EventListAdaptr extends BaseQuickAdapter<SimulationEvent, BaseViewHolder> {

        public EventListAdaptr() {
            super(R.layout.adapter_home_eventlist);
        }

        @Override
        protected void convert(BaseViewHolder helper, SimulationEvent item) {
            final Bitmap bitmap = mFaultImages.get(item.getEventIcon());//Bitmap位图包括像素以及长、宽、颜色等描述信息
            Log.v("item",item.getEventIcon().toString());
            if (bitmap == null) {
//                helper.setVisible(R.id.iv_adapter_faultIcon, false);
//                helper.setVisible(R.id.tv_adapter_placeHolder, true);
                helper.setVisible(R.id.iv_adapter_faultIcon, true);//图片
               helper.setVisible(R.id.tv_adapter_placeHolder, false);//显示找不到图片

               // Log.v("bitmap",mFaultImages.get(item.getEventIcon()).toString());//显示三次或五次,bitmap.toString()会报错
            } else {
                helper.setVisible(R.id.iv_adapter_faultIcon, true);
                helper.setVisible(R.id.tv_adapter_placeHolder, false);
                helper.setImageBitmap(R.id.iv_adapter_faultIcon, bitmap);
                Log.v("bitmap","bitmap！=null");
            }
            helper.setText(R.id.tv_adapter_faultName, item.getEventName());//在不连接的情况下也能改变名字，但是bitmap==null
            Log.v("bitmap",item.getEventName());
        }
    }

    private void initEventListPanel() {
        mEventsAdapter = new EventListAdaptr();
        mRvEventList.setAdapter(mEventsAdapter);
        mEventsAdapter.setOnItemClickListener((BaseQuickAdapter adapter, View view, int position) -> {
            SimulationEvent event = mEventsAdapter.getData().get(position);
            final long timeMillis = System.currentTimeMillis();
            if (lastTimeCarLatLng == null) {
                shortToast("没有车的位置信息");
                return;
            }
            if (TextUtils.isEmpty(mVehicleId)) {
                shortToast("没有获取到车机Id");
                return;
            }
            final LatLng carLocation = new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude());
            final RoadInfoHistory roadInfoHistory = RoadInfoHistory.getRoadInfoHistory(carLocation);
            String resultContent = event.getContent()
                    .replace("\"#VID#\"", this.mVehicleId)
                    .replace("\"#LO#\"", lastTimeCarLatLng.getLongitude() + "")
                    .replace("\"#LA#\"", lastTimeCarLatLng.getLatitude() + "")
                    .replace("\"#RID#\"", roadInfoHistory == null ? "-1" : getRoadIdWithInteger(roadInfoHistory.getRoadName()))
                    .replace("\"#LID#\"", roadInfoHistory == null ? "-1" : roadInfoHistory.getRoadLine(carLocation) + "")
                    .replace("\"#T#\"", timeMillis + "");

            publishContnet(Constants.CLIENT_1, event.getTopic(), resultContent);
            shortToast("上报成功");
        });

    }

    private String getRoadIdWithInteger(String roadName) {
        if (!TextUtils.isEmpty(roadName)) {
            return roadName.replace("road", "").replace("ROAD", "");
        }
        return "-1";
    }

//按钮事件
    private void initModePanel() {
        mRgModes.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            if (mLastTimeCheckedId == checkedId) {
                return;
            }
            switch (checkedId) {
                case R.id.rb_mode_normal:
                    onNormalMode();
                    break;
                case R.id.rb_mode_simulation:
                    onSimulationMode();
                    break;
                case R.id.rb_mode_onlineCarHailing:
                    onOnLineCarMode();
                    break;
            }
            mLastTimeCheckedId = checkedId;
//            mSlvDown.updateStatus(getRandomDirection(), getRandomCountdown(), SignalLampView);
//            mSlvTop.updateStatus(getRandomDirection(), getRandomCountdown(), getRandomIsAllow());
//            mSlvLeft.updateStatus(getRandomDirection(), getRandomCountdown(), getRandomIsAllow());
//            mSlvRight.updateStatus(getRandomDirection(), getRandomCountdown(), getRandomIsAllow());
        });
    }

    private void onNormalMode() {
        if (mRvEventList.getVisibility() != View.INVISIBLE) {
            outAnimator(mRvEventList);
        }
        if (mLlOnOrOffCar.getVisibility() != View.INVISIBLE) {
            outAnimator(mLlOnOrOffCar);
        }
    }

    private void onSimulationMode() {
        if (TextUtils.isEmpty(mFaultConfigJsonStr)) {
            mFaultConfigJsonStr = FileIOUtils.readFile2String(new File(mFaultConfigDir, Constants.FAULT_CONFIG_FILENAME));//Constants中定义
            Log.v(" mFaultConfigJsonStr",this.mFaultConfigJsonStr+Constants.FAULT_CONFIG_FILENAME);//没有执行
        }
        if (mFaultImages.size() == 0) {
           // Log.v(" mFaultConfigJsonStr",this.mFaultConfigJsonStr);
            // faultConfig.txt的并没有更新，应该是只能读不能写，并且自动保存，修改名字可以解决，修改回去就还是原来的，更换gradle比如换一台机器可以改变文字
           // Log.v( "123",Constants.FAULT_CONFIG_FILENAME);  会超时
           //listFiles:[Ljava.io.File;@99cf6ec    getName():imgs
            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.daoludahua);
            String eventstring1="daoludahua.png";
            mFaultImages.put(eventstring1,bitmap1);
            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(),R.drawable.daolushigong);
            String eventstring2="daolushigong.png";
            mFaultImages.put(eventstring2,bitmap2);
            Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(),R.drawable.weixiancheliang);
            String eventstring3="weixiancheliang.png";
            mFaultImages.put(eventstring3,bitmap3);
            Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(),R.drawable.zhangaiwu);
            String eventstring4="zhangaiwu.png";
            mFaultImages.put(eventstring4,bitmap4);
            Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(),R.drawable.alert_message_bus);
            String eventstring5="test.png";
            mFaultImages.put(eventstring5,bitmap5);
                //Log.v( "1234", "test");//没有执行

            //将文件图片写入手机sd卡，在独处
           /*  mFaultConfigImgsDir.listFiles((File pathname) -> {//java箭头函数，lambda表达式
                final String absolutePath = pathname.getAbsolutePath();
                Log.v("pathname",pathname.toString());
                 Log.v("pathname",absolutePath);
                if (absolutePath.endsWith(".png")) {
                    mFaultImages.put(pathname.getName(), BitmapFactory.decodeFile(absolutePath));
                    //Log.v( "1234", "test");//没有执行
                }
              // Log.v( "123", "test");//没有执行
                return false;
            });*/

           // Log.v( "1234", mFaultConfigImgsDir.toString());  // 显示/storage/emulated/0/QDCloudControl/faultConfig/imgs，storage/emulated/0是设备内存
        }

        final ArrayList<SimulationEvent> events = new ArrayList<>();
        try {
            final org.json.JSONArray jsonArray = new org.json.JSONArray(mFaultConfigJsonStr);//faultConfig.txt定义了上报事件,但是修改后界面不变
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject jsonObject = jsonArray.optJSONObject(i);
                final SimulationEvent simulationEvent = new SimulationEvent(
                        jsonObject.optString("EventName"),
                        jsonObject.optString("EventIcon"),
                        jsonObject.optString("Topic"),
                        jsonObject.optString("Content"));
                        Log.v( "12345", jsonObject.toString());
                events.add(simulationEvent);
            }
            if (events.size() > 0) {
                if (mRvEventList.getVisibility() != View.VISIBLE) {//!=
                    mEventsAdapter.setNewData(events);
                   // Log.v("123",events.toString());
                    inAnimator(mRvEventList);
                }
            }
            if (mLlOnOrOffCar.getVisibility() != View.INVISIBLE) {
                outAnimator(mLlOnOrOffCar);
            }
        } catch (JSONException e) {
            shortToast("仿真事件配置文件解析失败");
            e.printStackTrace();
        }
    }

    private void onOnLineCarMode() {
        if (mLlOnOrOffCar.getVisibility() != View.VISIBLE) {//!=
            inAnimator(mLlOnOrOffCar);
        }
        if (mRvEventList.getVisibility() != View.INVISIBLE) {
            outAnimator(mRvEventList);
        }
    }

    private boolean getRandomIsAllow() {
        return (((int) (Math.random() * 10))) % 2 == 0;
    }

    private String getRandomCountdown() {
        return String.valueOf(((int) (Math.random() * 120)));
    }

    private int getRandomDirection() {
        return ((int) (Math.random() * 10)) % 4;
    }

    private void outAnimator(View view) {
        view.setVisibility(View.INVISIBLE);
        final SlideOutRightAnimator slideOutRightAnimator = new SlideOutRightAnimator();
        slideOutRightAnimator.prepare(view);
        slideOutRightAnimator.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                slideOutRightAnimator.removeAllListener();
//                view.setVisibility(View.INVISIBLE);  //放到这里会有bug
            }
        });
        slideOutRightAnimator.setDuration(400);
        slideOutRightAnimator.start();
    }

    private void inAnimator(View view) {
        view.setVisibility(View.VISIBLE);
        final SlideInRightAnimator slideInRightAnimator = new SlideInRightAnimator();
        slideInRightAnimator.prepare(view);
        slideInRightAnimator.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation, boolean isReverse) {
                slideInRightAnimator.removeAllListener();
            }
        });
        slideInRightAnimator.setDuration(400);
        slideInRightAnimator.start();
    }
//弹出警告位置布置
    private void reLayoutAlertMessagePanel() {
        mLlAlertMessageTop1.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mLlAlertMessageTop1.getViewTreeObserver().removeOnPreDrawListener(this);
                final ViewGroup.LayoutParams layoutParamsRoot = mLlAlertMessageRoot.getLayoutParams();
                layoutParamsRoot.width = (int) (mLlAlertMessageTop1.getHeight() * 0.83f);
                mLlAlertMessageRoot.requestLayout();
                mTvAlertMessageTop2.getLayoutParams().height = (int) (mLlAlertMessageTop1.getHeight() * 0.13333f);
                mTvAlertMessageTop3.getLayoutParams().height = (int) (mLlAlertMessageTop1.getHeight() * 0.13333f);
                mTvAlertMessageTop2.requestLayout();
                mTvAlertMessageTop3.requestLayout();
                return true;
            }
        });
    }

    private void reLayoutSignalLampPanel() {
        mRgModes.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mRgModes.getViewTreeObserver().removeOnPreDrawListener(this);//获取按钮高度
                mRgModes.getLeft();
                final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLlSignalLampWrapper.getLayoutParams();
                layoutParams.rightMargin = ((FrameLayout.LayoutParams) ((View) mRgModes.getParent()).getLayoutParams()).rightMargin + mRgModes.getMeasuredWidth() + SizeUtils.dp2px(60);
                mLlSignalLampWrapper.requestLayout();
                return false;
            }
        });
    }

    private void reLocation() {
//        if (mBaiduMap != null && CheckUtils.locationIsValid(this.myLatLng)) {
//            final MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLngZoom(myLatLng, Constants.DEFAULT_LEVEL);
//            mBaiduMap.animateMapStatus(mapStatusUpdate);
//        } else {
//            shortToast("map equal null or latlng unvalid");
//        }
    }


    private QDMapStatusChangeListener mapStatusChangeListener = new QDMapStatusChangeListener() {
        @Override
        public void onMapStatusChangeFinish(MapStatus mapStatus) {
            super.onMapStatusChangeFinish(mapStatus);
            mIvCompass.setRotation(0-mapStatus.rotate);
        }
    };

    @Override
    public void onMapViewCreateFinish(MapView mapView) {
      /*  //
        List<String> strings = ResourceUtils.readAssets2List("offlineData/routeplan.txt");
        Log.e("offline",strings.toString());*/

        mBaiduMap = mapView.getMap();
        mBaiduMap.setOnMapStatusChangeListener(mapStatusChangeListener);
        mBaiduMap.setMyLocationEnabled(true);//开启定位图层
        /*以下代码可行
        LatLng point1 = new LatLng(39.963175, 116.400244);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_direction_arrow);
//构建MarkerOption，用于在地图上添加Marker

        OverlayOptions option = new MarkerOptions()
                .position(point1)
                .icon(bitmap)
                .draggable(true)
//设置平贴地图，在地图中双指下拉查看效果
                .flat(true)
                .alpha(0.5f);
//在地图上添加Marker，并显示
        Log.v(TAG,"point1:"+point1+" "+"bitmap:"+bitmap.toString()+" "+"option:"+option.toString()+" ");
        mBaiduMap.addOverlay(option);
        */
        final int color = Color.argb(0XFF, 0X61, 0XBB, 0XE7);
        Log.v(TAG,"定义图像");
//        mCarArrowBitmapDescrption = BitmapDescriptorFactory.fromResource(R.drawable.ic_direction_arrow);
//        else
            mCarArrowBitmapDescrption = BitmapDescriptorFactory.fromResource(R.drawable.ic_direction_arrow_small);
        mCarArrowBitmapDescrptionbig= BitmapDescriptorFactory.fromResource(R.drawable.ic_direction_arrow);
        //mCarArrowBitmapDescrption = BitmapDescriptorFactory.fromResource(R.drawable.startpoint);
        Log.v(TAG,"yi定义图像");
       MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, mCarArrowBitmapDescrption,100, color);
        mBaiduMap.setMyLocationConfiguration(myLocationConfiguration);
        mBaiduMap.setOnMapTouchListener((MotionEvent motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mHandler.removeMessages(WHAT_UPDATE_CAR_LOCATION);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    mHandler.sendEmptyMessageDelayed(WHAT_UPDATE_CAR_LOCATION, DELAY_UPDATE_CAR_LOCATION);
                    break;
            }
        });

    }

    public void showStartMarker(double gpsLat, double gpsLong, int bitmapResource) {
        //定义Maker坐标点
//        LatLng point = new LatLng(39.963175, 116.400244);
        LatLng point = convertLatLng(new LatLng(gpsLat, gpsLong));
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.drawable.startpoint)
                .fromResource(bitmapResource);

//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap)
                .alpha(0f);//起始点隐藏
//在地图上添加Marker，并显示
        if (startOverlay != null) {
            startOverlay.remove();
            startOverlay = null;
        }
        startOverlay = mBaiduMap.addOverlay(option);
    }

    public void showEndMarker(double gpsLat, double gpsLong, int bitmapResource) {
        //定义Maker坐标点
//        LatLng point = new LatLng(39.963175, 116.400244);
        LatLng point = convertLatLng(new LatLng(gpsLat, gpsLong));
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
//                .fromResource(R.drawable.startpoint)
                .fromResource(bitmapResource);

//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap)
                .alpha(0.8f);
//在地图上添加Marker，并显示
        if (endOverlay != null) {
            endOverlay.remove();
            endOverlay = null;
        }
        endOverlay = mBaiduMap.addOverlay(option);
    }

    private void setupDrivingLine(List<LatLng> latLngs) {

       /*采用绘制线的方法绘制点*/
        if (mOldOverline != null) {
            mOldOverline.remove();
        }
        Log.v(TAG, "line路线绘制");
       /*   //添加纹理
        List<BitmapDescriptor> textureList = new ArrayList<>();
        textureList.add(mRedTexture);
        List<Integer> indexList = new ArrayList<>();
        indexList.add(0);*/
        OverlayOptions ooPolyline = new PolylineOptions().width(15).color(Color.rgb(0X01, 0X9B, 0X4B)).points(latLngs);
        mOldOverline = mBaiduMap.addOverlay(ooPolyline);
        updateMapRotation();
    }
    //驾车luixna
    private void BaidusetupDrivingLine(double startLat,double startLon,double endLat,double endLon){
                         RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
                       Log.v(TAG, "路线");
                       OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
                           @Override
                           public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

                           }

                           @Override
                           public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

                           }

                           @Override
                           public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

                           }

                           @Override
                           public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
                               if (drivingRouteResult == null ) {
                                  Log.v(TAG, "抱歉，未找到结果");
                               }
                               //创建DrivingRouteOverlay实例
                               DrivingRouteOverlay overlay = new DrivingRouteOverlay(mBaiduMap);
                               if (drivingRouteResult.getRouteLines().size() > 0) {
                                   Log.v(TAG, "路线size() > 0");
                                   //获取路径规划数据,(以返回的第一条路线为例）
                                   //为DrivingRouteOverlay实例设置数据
                                   overlay.setData(drivingRouteResult.getRouteLines().get(0));
                                   //在地图上绘制DrivingRouteOverlay
                                   overlay.addToMap();
                               }else{
                                   Log.v(TAG, "路线size() = 0");
                               }
                           }

                           @Override
                           public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

                           }

                           @Override
                           public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

                           }
                       };
                       Log.v(TAG,"开始监听路线规划");
                       mSearch.setOnGetRoutePlanResultListener(listener);

                      // 定位当前位置后在进行路径规划
                              LatLng startpoint = convertLatLng(new LatLng(startLat, startLon));
                              LatLng endpoint = convertLatLng(new LatLng(endLat, endLon));
                       PlanNode starNode = PlanNode.withLocation(startpoint);
                       PlanNode endNode = PlanNode.withLocation(endpoint);

                       mSearch.drivingSearch((new DrivingRoutePlanOption())
                               .from(starNode)
                               .to(endNode));

                       //updateMapRotation();
                       mSearch.destroy();

    }

    private void updateMapRotation() {
        if (lastTimeCarLatLng == null) return;
        //旋转范围 0-360
        final double direction = lastTimeCarLatLng.getDirection();
        MapStatus newRotate = new MapStatus.Builder().rotate((float) (180 - direction)).build();
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(newRotate));
    }

    private void setupCarLocation(BaiduMap mBaiduMap, LatLng carLatLng) {
        carLatLng = convertLatLng(carLatLng);//坐标转换
//        final MyLocationData myLocationData = new MyLocationData.Builder().latitude(targetLatlng.latitude).longitude(targetLatlng.longitude).direction(-45).build();
//        mBaiduMap.setMyLocationData(myLocationData);
//
//
        Log.v(TAG,"处理后的carLatLng："+carLatLng);
        //根据地图选择合适的箭头大小
        MarkerOptions markerOptions = new MarkerOptions().position(carLatLng).rotate(45).icon(mCarArrowBitmapDescrption);
      if(mBaiduMap.getMapStatus().zoom>19){
          markerOptions=new MarkerOptions().position(carLatLng).rotate(45).icon(mCarArrowBitmapDescrptionbig);
      }
            // final MarkerOptions markerOptions = new MarkerOptions().position(carLatLng).rotate(45).icon(mCarArrowBitmapDescrption);

        //下面是测试代码
//        BitmapDescriptor bitmap6 = BitmapDescriptorFactory
//                .fromResource(R.drawable.ic_direction_arrow);

//        final OverlayOptions markerOptions = new MarkerOptions()
//                .position(carLatLng)
//                .icon(bitmap6);
        Log.v(TAG,"处理后的markerOptions："+markerOptions+",,"+markerOptions.toString());
        if (mCarOverlay != null) {
            mCarOverlay.remove();
            mCarOverlay = null;
            }
        mCarOverlay = mBaiduMap.addOverlay(markerOptions);
        //  mBaiduMap.addOverlay(markerOptions);
//        final Point point = mBaiduMap.getProjection().toScreenLocation(carLatLng);
//        point.x -= SizeUtils.dp2px(0);
//        point.y -= SizeUtils.dp2px(220);
//        final LatLng latLng = mBaiduMap.getProjection().fromScreenLocation(point);
//        final MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
//        mBaiduMap.animateMapStatus(mapStatusUpdate);
//      //  mBaiduMap.setMapStatus(mapStatusUpdate);
//        updateMapRotation();
//        final double direction = lastTimeCarLatLng.getDirection();
//        mIvCompass.setRotation((float) (180 - direction));
        //改变地图中心点显示
        LatLng cenpt = new LatLng(carLatLng.latitude,carLatLng.longitude);//设定中心点坐标
        final float direction = (float)lastTimeCarLatLng.getDirection();
 MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
.target(cenpt)
         .rotate(direction)
//.zoom(18.5f)
.build();//定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
mBaiduMap.setMapStatus(mMapStatusUpdate);//改变地图状态
      mIvCompass.setRotation((float) (0-direction));//TODO 后端数据准确，指南针随后端改变，否则随地图变化
    }

    private LatLng convertLatLng(LatLng sourceLatLng) {
        // 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
//        CoordinateConverter converter  = new CoordinateConverter();
//        converter.from(CoordType.COMMON);
// sourceLatLng待转换坐标
//        converter.coord(sourceLatLng);
//        LatLng desLatLng = converter.convert();

// 将GPS设备采集的原始GPS坐标转换成百度坐标
//        CoordinateConverter converter = new CoordinateConverter();
//        converter.from(CoordinateConverter.CoordType.GPS);
//// sourceLatLng待转换坐标
//        converter.coord(sourceLatLng);
//        LatLng desLatLng = converter.convert();
//        return desLatLng;
//百度地图文档2020/4/10
        //初始化坐标转换工具类，指定源坐标类型和坐标数据
// sourceLatLng待转换坐标,原始GPS坐标转换成百度坐标(两者差距在于百度重新包装，有略微的偏移

        CoordinateConverter converter = new CoordinateConverter()
                .from(CoordinateConverter.CoordType.GPS)
                .coord(sourceLatLng);

//desLatLng 转换后的坐标
        LatLng desLatLng = converter.convert();

      return desLatLng;
      //  return sourceLatLng;//此路是百度坐标
    }

    //test函数
    public void testAlert() {
   /*     //carid
        VehicleIdRes testvehicleIdRes =new VehicleIdRes();
        testvehicleIdRes.setYktype("vid");
        testvehicleIdRes.setValue("10008");
        mVehicleId=testvehicleIdRes.getValue();
//test车辆位置
        isUpdateCarLocation=true;
        CarLatLngRes eCarLatLng = new CarLatLngRes();
        eCarLatLng.setLongitude(121.22972254);
        eCarLatLng.setLatitude(31.33209647);
        eCarLatLng.setDirection(90);
        Log.v(TAG," onCarPostionChanged");
        onCarPostionChanged(eCarLatLng);*/
   // 线路信息
        StartDestinationRes testdestinationRes = new StartDestinationRes();
        testdestinationRes.setStartLat(31.33209647);
        testdestinationRes.setStartLon(121.22972254);
        testdestinationRes.setEndLat(31.33010155);
        testdestinationRes.setEndLon(121.22346433);
        testdestinationRes.setOrderId(6);
        getDestinationRes(testdestinationRes);

    }
}



