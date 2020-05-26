package com.tusi.qdcloudcontrol.modle;

import android.graphics.Color;
import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.tusi.qdcloudcontrol.R;
import com.tusi.qdcloudcontrol.common.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linfeng on 2018/10/18  12:40
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class AlertMessage {
    public int mEventType;
    public long mTimeStamp;
    public double mLat;
    public double mLon;
    public String mUniqueId;
    public String mEventName;
    public int level;
    public static final int LEVEL_DANGEROUS = 3;//危险
    public static final int LEVEL_WARRINNG = 2;//警告
    public static final int LEVEL_TIP = 1;//提示
    public static final int LEVEL_STATUS = 0;
    public long receiveTime;
    public long enqueueTime;
    public long startTime;
    public boolean isPlayed = false;
    public String extra;

    public AlertMessage() {
        this.receiveTime = System.currentTimeMillis();
    }

    public static AlertMessage parse(String json) {
        final AlertMessage alertMessage = new AlertMessage();


        return alertMessage;
    }

    public static List<AlertMessage> fromRowEntity(Object rawObject) {
        final List alertMessages = new ArrayList<AlertMessage>();

        if (rawObject instanceof RoadConstructionRes) {
            final AlertMessage alertMessage = new AlertMessage();
            final RoadConstructionRes rawObject1 = (RoadConstructionRes) rawObject;
            alertMessage.mEventName = Constants.ENAME_DAO_LU_SHI_GSON;
            alertMessage.mEventType = rawObject1.getEventType();
            alertMessage.mLat = rawObject1.getPosition().getLatitude();
            alertMessage.mLon = rawObject1.getPosition().getLongitude();
            alertMessage.mTimeStamp = rawObject1.getTimestamp();
            alertMessage.mUniqueId = rawObject1.getUniqueId();
            alertMessage.level = LEVEL_WARRINNG;
            alertMessages.add(alertMessage);
        } else if (rawObject instanceof RoadSkidRes) {
            final AlertMessage alertMessage = new AlertMessage();
            final RoadSkidRes rawObject1 = (RoadSkidRes) rawObject;
            alertMessage.mEventName = Constants.ENAME_DAO_LU_DA_HUA;
            alertMessage.mEventType = rawObject1.getEventType();
            alertMessage.mLat = rawObject1.getPosition().getLatitude();
            alertMessage.mLon = rawObject1.getPosition().getLongitude();
            alertMessage.mTimeStamp = rawObject1.getTimestamp();
            alertMessage.mUniqueId = rawObject1.getUniqueId();
            alertMessage.level = LEVEL_DANGEROUS;
            alertMessages.add(alertMessage);
        } else if (rawObject instanceof RoadBarriersRes) {
            final AlertMessage alertMessage = new AlertMessage();
            final RoadBarriersRes rawObject1 = (RoadBarriersRes) rawObject;
            alertMessage.mEventName = Constants.ENAME_ZHANG_AI_WU;
            alertMessage.mEventType = -1;
            alertMessage.mLat = rawObject1.getPosition().getLatitude();
            alertMessage.mLon = rawObject1.getPosition().getLongitude();
            alertMessage.mTimeStamp = rawObject1.getTimestamp();
            alertMessage.mUniqueId = rawObject1.getUniqueId();
            alertMessage.level = LEVEL_TIP;
            alertMessages.add(alertMessage);
        } else if (rawObject instanceof BusRoadRes) {
            final AlertMessage alertMessage = new AlertMessage();
            final BusRoadRes rawObject1 = (BusRoadRes) rawObject;
            alertMessage.mEventName = Constants.ENAME_DONG_JIAO_CHE_DAO;
            alertMessage.mEventType = -1;
            alertMessage.mLat = rawObject1.getRoad().getStartPosition().getLatitude();
            alertMessage.mLon = rawObject1.getRoad().getStartPosition().getLongitude();
            alertMessage.mTimeStamp = -1;
            try {
                alertMessage.mUniqueId = rawObject1.getRoad().getRoad().get(0).getRoadId() + "";
            } catch (Exception e) {
            }
            alertMessage.level = LEVEL_WARRINNG;
            alertMessages.add(alertMessage);
        } else if (rawObject instanceof GangerousCarRes) {
            final GangerousCarRes rawObject1 = (GangerousCarRes) rawObject;
            final List<GangerousCarRes.VehiclesBean> vehicles = rawObject1.getVehicles();
            for (GangerousCarRes.VehiclesBean vehicle : vehicles) {
                if (vehicle == null) continue;
                final AlertMessage alertMessage = new AlertMessage();
                alertMessage.mEventName = Constants.ENAME_WEI_XIAN_CHE_LIANG;
                alertMessage.mEventType = -1;
                final GangerousCarRes.VehiclesBean.PositionBean position = vehicle.getPosition();
                alertMessage.mLat = vehicle.getPosition().getLatitude();
                alertMessage.mLon = vehicle.getPosition().getLongitude();
                alertMessage.mTimeStamp = -1;
                alertMessage.mUniqueId = vehicle.getVehicleId() + "";
                alertMessage.level = LEVEL_WARRINNG;
                alertMessage.extra = String.format("%d,%d,%d,%d,", vehicle.getUserBrake(), vehicle.getUserCutin(), vehicle.getUserSpeed(), vehicle.getUserinfraction());
                alertMessages.add(alertMessage);
            }

        }
        return alertMessages;
    }

    public int getLevelColor() {
        switch (this.level) {
            case LEVEL_DANGEROUS:
                return Color.parseColor("#CF3E18");
            case LEVEL_WARRINNG:
                return Color.parseColor("#FD8611");
            case LEVEL_TIP:
                return Color.parseColor("#FDB211");
            case LEVEL_STATUS:
                return Color.parseColor("#FDB211");
            default:
                return Color.parseColor("#FDB211");
        }

    }


    public int getMessageIconByMsgName() {
        switch (mEventName) {
            case Constants.ENAME_DAO_LU_SHI_GSON:
                return R.drawable.alert_message_daolushigongn;
            case Constants.ENAME_DAO_LU_DA_HUA:
                return R.drawable.alert_message_daoludahua;
            case Constants.ENAME_DONG_JIAO_CHE_DAO:
                return R.drawable.alert_message_bus;
            case Constants.ENAME_WEI_XIAN_CHE_LIANG:
                return R.drawable.alert_message_weixiancheliang;
            case Constants.ENAME_ZHANG_AI_WU:
                return R.drawable.alert_message_zhangaiwu;
        }
        return 0;
    }


    public String getLevelName() {
        switch (this.level) {
            case AlertMessage.LEVEL_DANGEROUS:
                return "危险";
            case AlertMessage.LEVEL_WARRINNG:
                return "警告";
            case AlertMessage.LEVEL_TIP:
                return "提示";
        }
        return "";
    }


    public String getSpeakContent(LatLng carLatLng) {
        if (carLatLng != null) {
            final LatLng eventLatLng = new LatLng(this.mLat, this.mLon);

            final boolean targetIsBefore = RoadLineData.targetIsBefore(carLatLng, eventLatLng);

            final RoadInfoHistory carRoadInfoHistory = RoadInfoHistory.getRoadInfoHistory(carLatLng);
            final RoadInfoHistory eventRoadInfoHistory = RoadInfoHistory.getRoadInfoHistory(eventLatLng);
            final int totalLine = eventRoadInfoHistory.getRoadTotalLine();
            StringBuilder direction = new StringBuilder();
            if ((carRoadInfoHistory == null) || (eventRoadInfoHistory == null)) {
                return "车辆位置丢失";
            } else {
                final int carLaneLine = carRoadInfoHistory.getRoadLine(carLatLng);
                final int eventLaneLine = eventRoadInfoHistory.getRoadLine(eventLatLng);

                if (totalLine == 3) {
                    if (eventLaneLine == 1) {
                        direction.append("右侧车道");
                    } else if (eventLaneLine == 3) {
                        direction.append("左侧车道");
                    } else if (eventLaneLine == 2) {
                        direction.append("中间车道");
                    }
                } else if (totalLine == 2) {
                    if (eventLaneLine == 1) {
                        direction.append("右侧车道");
                    } else if (eventLaneLine == 2) {
                        direction.append("左侧车道");
                    }
                } else if (totalLine == 1) {
//                direction.append("前方");
                }
                switch (this.mEventName) {
                    case Constants.ENAME_DAO_LU_SHI_GSON:
                        return String.format("前方%d米%s施工，请注意避让", ((int) DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(new LatLng(this.mLat, this.mLon)))), direction.toString());
                    case Constants.ENAME_DAO_LU_DA_HUA:
                        return String.format("前方%d米%s打滑，请减速慢行", ((int) DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(new LatLng(this.mLat, this.mLon)))), direction.toString());
                    case Constants.ENAME_ZHANG_AI_WU:
                        return String.format("前方%d米%s有障碍物，请注意避让", ((int) DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(new LatLng(this.mLat, this.mLon)))), direction.toString());
                    case Constants.ENAME_DONG_JIAO_CHE_DAO:
                        return String.format("前方%d米%s变为公交专用道，请注意换道", ((int) DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(new LatLng(this.mLat, this.mLon)))), direction.toString());
                    case Constants.ENAME_WEI_XIAN_CHE_LIANG:
                        direction.replace(0, direction.length(), "");
                        if (carLaneLine == eventLaneLine) {
                            direction.append("正");
                        } else if (carLaneLine > eventLaneLine) {
                            direction.append("左");
                        } else if (carLaneLine < eventLaneLine) {
                            direction.append("右");
                        }
                        if (targetIsBefore) {
                            direction.append("前");
                        } else {
                            direction.append("后");
                        }
                        direction.append("方");
                        return getDrivingBehavior(direction.toString());
                    default:
                        return "";
                }
            }

        }
        return "车辆位置丢失";
    }


    private String getDrivingBehavior(String directionn) {
        if (TextUtils.isEmpty(this.extra) || extra.split(",").length == 0) {
            return "前方驾驶有危险车辆，请注意避让";
        }

        final String[] split = extra.split(",");
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                try {
                    final int i1 = Integer.parseInt(split[0]);
                    if (i1 <= 2 && i1 >= 0) {
                        //这里可以不用加判断
                        stringBuilder.append("急刹车倾向,");
                    }
                } catch (Exception e) {
                    return String.format("%s车辆有%s请注意避让", directionn, stringBuilder.toString());
                }
            }
            if (i == 1) {
                try {
                    final int i1 = Integer.parseInt(split[1]);
                    if (i1 <= 2 && i1 >= 0) {
                        //这里可以不用加判断
                        stringBuilder.append("急切入倾向,");
                    }
                } catch (Exception e) {
                    return String.format("%s车辆有%s请注意避让", directionn, stringBuilder.toString());
                }
            }
            if (i == 2) {
                try {
                    final int i1 = Integer.parseInt(split[2]);
                    if (i1 <= 2 && i1 >= 0) {
                        //这里可以不用加判断
                        stringBuilder.append("超速驾驶倾向,");
                    }
                } catch (Exception e) {
                    return String.format("%s车辆有%s请注意避让", directionn, stringBuilder.toString());
                }
            }
            if (i == 3) {
                try {
                    final int i1 = Integer.parseInt(split[3]);
                    if (i1 <= 2 && i1 >= 0) {
                        //这里可以不用加判断
                        stringBuilder.append("违法驾驶行为,");
                    }
                } catch (Exception e) {
                    return String.format("%s车辆有%s请注意避让", directionn, stringBuilder.toString());
                }
            }
        }
        return String.format("%s车辆有%s请注意避让", directionn, stringBuilder.toString());
    }

    public int getSoundId() {
        switch (this.level) {
            case LEVEL_DANGEROUS:
                return 4;
            case LEVEL_WARRINNG:
                return 3;
            case LEVEL_TIP:
                return 2;
            case LEVEL_STATUS:
                return 1;
        }
        return 1;
    }

    private LatLng convertLatLng(LatLng sourceLatLng) {
        // 将google地图、soso地图、aliyun地图、mapabc地图和amap地图// 所用坐标转换成百度坐标
//        CoordinateConverter converter  = new CoordinateConverter();
//        converter.from(CoordType.COMMON);
// sourceLatLng待转换坐标
//        converter.coord(sourceLatLng);
//        LatLng desLatLng = converter.convert();

// 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
// sourceLatLng待转换坐标
        converter.coord(sourceLatLng);
        LatLng desLatLng = converter.convert();
        return desLatLng;
    }

    public double getDistanceToTarget(CarLatLngRes lastTimeCarLatLng) {
        final LatLng eventPosition = new LatLng(this.mLat, this.mLon);
        final LatLng targetPosition = new LatLng(lastTimeCarLatLng.getLatitude(), lastTimeCarLatLng.getLongitude());
        return DistanceUtil.getDistance(convertLatLng(eventPosition), convertLatLng(targetPosition));
    }

    public String getSpeakContentwithoutroad(LatLng carLatLng) {
        StringBuilder direction = new StringBuilder();
        switch (this.mEventName) {
            case Constants.ENAME_DAO_LU_SHI_GSON:
                return String.format("前方%d米%s施工，请注意避让", ((int) DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(new LatLng(this.mLat, this.mLon)))), direction.toString());
            case Constants.ENAME_DAO_LU_DA_HUA:
                return String.format("前方%d米%s打滑，请减速慢行", ((int) DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(new LatLng(this.mLat, this.mLon)))), direction.toString());
            case Constants.ENAME_ZHANG_AI_WU:
                return String.format("前方%d米%s有障碍物，请注意避让", ((int) DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(new LatLng(this.mLat, this.mLon)))), direction.toString());
            case Constants.ENAME_DONG_JIAO_CHE_DAO:
                return String.format("前方%d米%s变为公交专用道，请注意换道", ((int) DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(new LatLng(this.mLat, this.mLon)))), direction.toString());
            case Constants.ENAME_WEI_XIAN_CHE_LIANG:
                direction.replace(0, direction.length(), "");
                direction.append("前方");//如果没有道路id只播报前方数据
                return getDrivingBehavior(direction.toString());
            default:return "车联位置丢失";
        }
    }
    public String getSpeakContentwithoutroadweixian(LatLng carLatLng,LatLng pastcarLatLng) {
        StringBuilder direction = new StringBuilder();
        switch (this.mEventName) {
            case Constants.ENAME_WEI_XIAN_CHE_LIANG:
                direction.replace(0, direction.length(), "");
                if( DistanceUtil.getDistance(convertLatLng(carLatLng), convertLatLng(new LatLng(this.mLat, this.mLon))) <=DistanceUtil.getDistance(convertLatLng(pastcarLatLng), convertLatLng(new LatLng(this.mLat, this.mLon)))){
                    direction.append("前方");//如果没有道路id只播报前方数据
                }
               else{
                    direction.append("后方");
                }
                return getDrivingBehavior(direction.toString());
            default:return "车联位置丢失";
        }
    }

}
