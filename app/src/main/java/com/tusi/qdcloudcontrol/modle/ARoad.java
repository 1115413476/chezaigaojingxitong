package com.tusi.qdcloudcontrol.modle;

import android.text.TextUtils;

import com.baidu.mapapi.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linfeng on 2018/10/28  21:25
 * Email zhanglinfengdev@163.com
 * Function details...
 */
public class ARoad {
    public String roadName;
    public List<LatLng> latLngList;

    private ARoad() {
    }

    public static ARoad parse(String rawRoad) {
        try {
            final ARoad aRoad = new ARoad();
            final JSONObject jsonObject = new JSONObject(rawRoad);
            aRoad.roadName = jsonObject.optString("roadName");
            final ArrayList<LatLng> latLngs = new ArrayList<>();
            final String locations = jsonObject.optString("Locations");
            if (!TextUtils.isEmpty(locations)) {
                final String[] split = locations.split(",");
                for (int i = 0; i < split.length; i += 2) {
                    double lat = Double.parseDouble(split[i + 1]);
                    double lon = Double.parseDouble(split[i]);
                    final LatLng latLng = new LatLng(lat, lon);
                    latLngs.add(latLng);
                }
                aRoad.latLngList = latLngs;
            }
            return aRoad;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
