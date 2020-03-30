package com.tusi.qdcloudcontrol.modle;

import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.blankj.utilcode.util.ResourceUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class RoadLineData {

    private static List<String> roadIds = new ArrayList<>();
    private static Map<String[], String[]> startEndLatLngInfo = new HashMap<>();
    private static LatLng lastTimeLineStartLatLng;
    private static LatLng lastTimeLineEndLatLng;
    private static List<LatLng> currentShowLine;
    private static List<ARoad> mAllRoads;

    static {
        List<String> strings = ResourceUtils.readAssets2List("roadLinesData.txt");
        for (String string : strings) {
            if (TextUtils.isEmpty(string)) {
                continue;
            }

            final String rawStartEndLatLng = string.substring(string.indexOf("{(") + 1, string.indexOf("[")).replace("(", "").replace(")", "");
            String[] startEndLatLng = rawStartEndLatLng.split(",");

            int start2 = string.indexOf("[");
            int end2 = string.indexOf("]");
            String lineLatLngs = string.substring(start2 + 1, end2);
            startEndLatLngInfo.put(startEndLatLng, lineLatLngs.replace("(", "").replace(")", "").split(","));

            final String roadIdsStr = string.substring(string.lastIndexOf("[") + 1, string.lastIndexOf("]"));
            if (!TextUtils.isEmpty(roadIdsStr)) {
                roadIds.add(rawStartEndLatLng + "#" + roadIdsStr);
            }
        }


        mAllRoads = new ArrayList<>();
        final List<String> roadsConfig = ResourceUtils.readAssets2List("roadsConfig.txt");
        for (String rawARoad : roadsConfig) {
            if (!TextUtils.isEmpty(rawARoad)) {
                mAllRoads.add(ARoad.parse(rawARoad));
            }
        }
    }


    public static String getNextRoadIds(String currentRoadIds) {
        if (lastTimeLineStartLatLng == null || lastTimeLineEndLatLng == null || roadIds.size() == 0) {
            return "";
        }
        LatLng startLatLng = lastTimeLineStartLatLng;
        LatLng endLatLng = lastTimeLineEndLatLng;
        for (String roadId : roadIds) {
            final String[] split = roadId.split("#");
            if (TextUtils.isEmpty(split[0]) || TextUtils.isEmpty(split[1])) {
                return "";
            }
            final String[] split1 = split[0].split(",");
            boolean equalsStartLat = Double.parseDouble(split1[0]) == startLatLng.longitude;
            boolean equalsStartLon = Double.parseDouble(split1[1]) == startLatLng.latitude;
            boolean equalsEndLat = Double.parseDouble(split1[2]) == endLatLng.longitude;
            boolean equalsEndLon = Double.parseDouble(split1[3]) == endLatLng.latitude;

            if (equalsStartLat && equalsStartLon && equalsEndLat && equalsEndLon) {
                final String[] roadIds = split[1].split(",");
                for (int i = 0; i < roadIds.length; i++) {
                    final String id = roadIds[i];
                    if (id.trim().equalsIgnoreCase(currentRoadIds)) {
                        if (i != roadIds.length - 1) {
                            return roadIds[i + 1];
                        }
                    }
                }
            }
        }
        return "";
    }

    private static LatLng getNearbyPointDistants(List<LatLng> latLngs, LatLng latLng) {
        if (latLngs != null && latLngs.size() > 0) {
            final TreeMap<Double, LatLng> latLngDistanceMap = new TreeMap<Double, LatLng>();
            for (LatLng latLng_ : latLngs) {
                final double distance = DistanceUtil.getDistance(convertLatLng(latLng), convertLatLng(latLng_));
                latLngDistanceMap.put(distance, latLng_);
            }
            return latLngDistanceMap.firstEntry().getValue();
        } else {
            return null;
        }
    }

    public static boolean targetIsBefore(LatLng carLatLng, LatLng targetLatLng) {
        if (mAllRoads != null && mAllRoads.size() > 0) {
            final RoadInfoHistory carRoadInfoHistory = RoadInfoHistory.getRoadInfoHistory(carLatLng);
            final RoadInfoHistory eventRoadInfoHistory = RoadInfoHistory.getRoadInfoHistory(targetLatLng);
            if (carRoadInfoHistory != null && eventRoadInfoHistory != null) {
                if (carRoadInfoHistory.getRoadName().equalsIgnoreCase(eventRoadInfoHistory.getRoadName())) {
                    for (ARoad mAllRoad : mAllRoads) {
                        if (carRoadInfoHistory.getRoadName().equalsIgnoreCase(mAllRoad.roadName)) {
                            final LatLng targetNearbyPointDistants = getNearbyPointDistants(mAllRoad.latLngList, targetLatLng);
                            final LatLng carNearbyPointDistants = getNearbyPointDistants(mAllRoad.latLngList, carLatLng);
                            return mAllRoad.latLngList.indexOf(targetNearbyPointDistants) > mAllRoad.latLngList.indexOf(carNearbyPointDistants);
                        }
                    }
                }
            }
        }
        return false;
    }

    public static List<LatLng> findLineByBeginEndLatLng(LatLng start, LatLng end) {
        lastTimeLineStartLatLng = start;
        lastTimeLineEndLatLng = end;
        for (String[] strings : startEndLatLngInfo.keySet()) {
            double startLat = Double.parseDouble(strings[0]);
            double startLng = Double.parseDouble(strings[1]);

            double endLat = Double.parseDouble(strings[2]);
            double endLng = Double.parseDouble(strings[3]);

            boolean startEquals = start.longitude == startLat && start.latitude == startLng;
            boolean endEquals = end.longitude == endLat && end.latitude == endLng;
            if (startEquals && endEquals) {
                currentShowLine = getLatLngList(startEndLatLngInfo.get(strings));
                return currentShowLine;
            }
        }
        return null;
    }

    public static List<LatLng> convertToBaiduLatLons(List<LatLng> latLngList) {
        final ArrayList<LatLng> result = new ArrayList<>();
        for (LatLng latLng : latLngList) {
            result.add(convertLatLng(latLng));
        }
        return result;
    }

    private static List<LatLng> getLatLngList(String[] centerStrArray) {
        ArrayList<LatLng> latLngs = new ArrayList<>();
        for (int i = 0; i < centerStrArray.length - 1; i += 2) {
            double lat = 0;
            double lng = 0;
            lat = Double.parseDouble(centerStrArray[i + 1]);
            lng = Double.parseDouble(centerStrArray[i]);
            latLngs.add(new LatLng(lat, lng));
        }
        return latLngs;
    }

    private static LatLng convertLatLng(LatLng sourceLatLng) {
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
}