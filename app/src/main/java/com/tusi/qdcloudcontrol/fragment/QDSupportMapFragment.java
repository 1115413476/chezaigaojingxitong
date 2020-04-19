package com.tusi.qdcloudcontrol.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;

/**
 * Created by linfeng on 2018/6/9  23:58
 * Email zhanglinfengdev@163.com
 */
@SuppressLint("ValidFragment")
public class QDSupportMapFragment extends SupportMapFragment {

    private static final float DEFAULT_ZOOM = 18.5f;
    private final float mInitZoom;
    private MapViewCreatedFinishCallback callback;

    @SuppressLint("ValidFragment")
    public QDSupportMapFragment(float initZoom) {
        super();
        this.mInitZoom = initZoom;
    }

    public QDSupportMapFragment() {
        this.mInitZoom = DEFAULT_ZOOM;
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
//        YDMapUtils.setCustomMapStyle(getActivity());
        final View view = super.onCreateView(layoutInflater, viewGroup, bundle);
        final MapView mapView = getMapView();
        Log.v("callback1","callback is "+callback);
        //地图缩放等手势设置
        mapView.getMap().getUiSettings().setOverlookingGesturesEnabled(true);//俯视
        mapView.getMap().getUiSettings().setCompassEnabled(false);
        mapView.getMap().getUiSettings().setRotateGesturesEnabled(true);//地图旋转
        mapView.getMap().getUiSettings().setZoomGesturesEnabled(true);
        mapView.showZoomControls(false);
        mapView.getMap().setMapStatus(MapStatusUpdateFactory.zoomTo(mInitZoom));
        clearBaiduLogo(mapView);
        Log.v("callback2","callback is "+callback);
       // Log.v("callback","callback is "+callback);未执行
        if (callback != null) {
            this.callback.onMapViewCreateFinish(getMapView());
        }
        return view;
    }

    private void clearBaiduLogo(MapView mapView) {
        if (mapView != null) {
            View view = mapView.getChildAt(1);
            if (view != null && view instanceof ImageView) {
                view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        MapView.setMapCustomEnable(true);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
//        MapView.setMapCustomEnable(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static QDSupportMapFragment newInstance(float initZoom) {
        return new QDSupportMapFragment(initZoom);
    }

    public void setOnMapViewCreatedCallback(MapViewCreatedFinishCallback callback) {
        this.callback = callback;
    }

    public interface MapViewCreatedFinishCallback {
        void onMapViewCreateFinish(MapView mapView);
    }
}
