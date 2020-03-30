package com.tusi.qdcloudcontrol.modle;

import android.graphics.Point;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.tusi.qdcloudcontrol.QDApplication;

import java.util.ArrayList;

public class RoadInfoHistory {
    String roadName;
    String partId;
    LatLng latLng0;
    LatLng latLng1;
    LatLng latLng2;
    LatLng latLng3;
    private static final double LANE_WIDTH = 3.5;
    private final LatLngBounds mMultiPolygon;

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getPartId() {
        return partId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public RoadInfoHistory(String roadName, String partId, byte[] rgn_data) {
        this.roadName = roadName;
        this.partId = partId;

        int lng0 = bytesToInt2(rgn_data, 0);
        int lat0 = bytesToInt2(rgn_data, 4);
        latLng0 = new LatLng(lat0 / 10E6, lng0 / 1E7);

        int lng1 = bytesToInt2(rgn_data, 8);
        int lat1 = bytesToInt2(rgn_data, 12);
        latLng1 = new LatLng(lat1 / 10E6, lng1 / 1E7);

        int lng2 = bytesToInt2(rgn_data, 16);
        int lat2 = bytesToInt2(rgn_data, 20);
        latLng2 = new LatLng(lat2 / 10E6, lng2 / 1E7);

        int lng3 = bytesToInt2(rgn_data, 24);
        int lat3 = bytesToInt2(rgn_data, 28);
        latLng3 = new LatLng(lat3 / 10E6, lng3 / 1E7);

        mMultiPolygon = new LatLngBounds.Builder().include(this.latLng0).include(this.latLng1).include(this.latLng2).include(this.latLng3).build();

    }

    public static int bytesToInt2(byte[] src, int offset) {
        int value;
//        value = (int) (((src[offset] & 0xFF) << 24)
//                | ((src[offset + 1] & 0xFF) << 16)
//                | ((src[offset + 2] & 0xFF) << 8)
//                | (src[offset + 3] & 0xFF));
        value = ((src[offset + 3] & 0xFF) << 24)
                | ((src[offset + 2] & 0xFF) << 16)
                | ((src[offset + 1] & 0xFF) << 8)
                | (src[offset] & 0xFF);
        return value;
    }

    private LatLng[] getLatLngArray() {
        LatLng[] result = new LatLng[]{this.latLng0, this.latLng1, this.latLng2, this.latLng3};
        return result;
    }

    public boolean isContainer(LatLng targetLocation) {
        final LatLng[] latLngArray = getLatLngArray();
        if (latLngArray == null || targetLocation == null) {
            return false;
        }
        int latLngCount = latLngArray.length;
        int counter = 0;
        int i;
        double xinters;
        LatLng p1, p2;

        p1 = latLngArray[0];
        for (i = 1; i <= latLngCount; i++) {
            p2 = latLngArray[i % latLngCount];
            if (targetLocation.latitude > Math.min(p1.latitude, p2.latitude)) {
                //低
                if (targetLocation.latitude <= Math.max(p1.latitude, p2.latitude)) {
                    //高
                    if (targetLocation.longitude <= Math.max(p1.longitude, p2.longitude)) {
                        //右
                        if (p1.latitude != p2.latitude) {
                            //简单忽略平行X轴这种情况  //交叉点坐标 参考./media/point-and-polygon/xinters.jpg
                            xinters = (targetLocation.latitude - p1.latitude) * (p2.longitude - p1.longitude) / (p2.latitude - p1.latitude) + p1.longitude;
                            if (p1.longitude == p2.longitude || targetLocation.longitude <= xinters) {
                                counter++;
                            }
                        }
                    }
                }
            }
            p1 = p2;
        }

        return counter % 2 != 0;
    }

    /**
     * 判断p是否在abcd组成的四边形内
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param p
     * @return 如果p在四边形内返回true, 否则返回false.
     */
    public static boolean pInQuadrangle(MPoint a, MPoint b, MPoint c, MPoint d, MPoint p) {
        double dTriangle = triangleArea(a, b, p) + triangleArea(b, c, p)
                + triangleArea(c, d, p) + triangleArea(d, a, p);
        double dQuadrangle = triangleArea(a, b, c) + triangleArea(c, d, a);
        return dTriangle == dQuadrangle;
    }

    // 返回三个点组成三角形的面积
    private static double triangleArea(MPoint a, MPoint b, MPoint c) {
        double result = Math.abs((a.x * b.y + b.x * c.y + c.x * a.y - b.x * a.y
                - c.x * b.y - a.x * c.y) / 2.0D);
        return result;
    }


    public static boolean pInQuadrangle_(Point a, Point b, Point c, Point d, Point p) {
        double dTriangle = triangleArea_(a, b, p) + triangleArea_(b, c, p)
                + triangleArea_(c, d, p) + triangleArea_(d, a, p);
        double dQuadrangle = triangleArea_(a, b, c) + triangleArea_(c, d, a);
        return dTriangle == dQuadrangle;
    }

    // 返回三个点组成三角形的面积
    private static double triangleArea_(Point a, Point b, Point c) {
        double result = Math.abs((a.x * b.y + b.x * c.y + c.x * a.y - b.x * a.y
                - c.x * b.y - a.x * c.y) / 2.0D);
        return result;
    }

    public boolean isContainerV2(LatLng targetLocation) {
        final Point a = new Point(((int) (latLng0.longitude * 1E7)), ((int) (latLng0.latitude * 1E7)));
        final Point b = new Point(((int) (latLng1.longitude * 1E7)), ((int) (latLng1.latitude * 1E7)));
        final Point c = new Point(((int) (latLng2.longitude * 1E7)), ((int) (latLng2.latitude * 1E7)));
        final Point d = new Point(((int) (latLng3.longitude * 1E7)), ((int) (latLng3.latitude * 1E7)));
        final Point p = new Point(((int) (targetLocation.longitude * 1E7)), ((int) (targetLocation.latitude * 1E7)));
        final boolean result = pInQuadrangle_(a, b, c, d, p);
        return result;
    }

    public boolean isContainerV3(LatLng targetLatLng) {
//        String wktPoly = "POLYGON ((30 10, 40 40, 20 40, 10 20, 30 10))"; //请自行搜素了解wkt格式
//        String wktPoint = "POINT (30 30)";
//        WKTReader reader = new WKTReader(JTSFactoryFinder.getGeometryFactory());
//        GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
//        Geometry point = reader.read(wktPoint);
//        Geometry poly = reader.read(wktPoly);
//        return poly.contains(point); //返回true或false
        return this.mMultiPolygon.contains(targetLatLng);
    }

    public static class MPoint {
        public double x;
        public double y;

        public MPoint(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
// east, north单位为米
//    void GpsTranXy(double lonNow, double latNow, double lonBase, double latBase, double& east, double& north)
//    {
//        // 纬度转为距离米的系数
//        double dBToM = 111193;
//        // 经度转为距离米的系数
//        double dLToM = dBToM * cos(latNow / 57.3);
//        east = (lonNow - lonBase) * dLToM;
//        north = (latNow - latBase) * dBToM;
//    }

    // east, north单位为米
    private double[] getXYDistans(double lonNow, double latNow, double lonBase, double latBase) {
        // 纬度转为距离米的系数
        double dBToM = 111193;
        // 经度转为距离米的系数
        double dLToM = dBToM * Math.cos(latNow / 57.3);
        double east = (lonNow - lonBase) * dLToM;
        double north = (latNow - latBase) * dBToM;
        return new double[]{east, north};
    }
//
    // 计算点到直线的距离,左为正，右为负
//    double get_dis_ptLine(Point2f pt0, Point2f pt1, Point2f pt2) {
//        double x0, x1, x2, y0, y1, y2;
//        x1 = y1 = 0;
//        GpsTranXy(pt2.x, pt2.y, pt1.x, pt1.y, x2, y2);
//        GpsTranXy(pt0.x, pt0.y, pt1.x, pt1.y, x0, y0);
//        double d = ((y1 - y2) * x0 + (x2 - x1) * y0 + x1 * y2 - x2 * y1) / sqrt(pow(y2 - y1, 2) + pow(x2 - x1, 2));
//        return d;
//    }

    private double getDistanceWithBaseLine(LatLng targetLocation) {
        LatLng pt0 = targetLocation;
        LatLng pt1 = this.latLng0;
        LatLng pt2 = this.latLng1;

        final double[] xyDistans_ = getXYDistans(pt0.longitude, pt0.latitude, pt1.longitude, pt1.latitude);
        final double[] xyDistans = getXYDistans(pt2.longitude, pt2.latitude, pt1.longitude, pt1.latitude);
        double x0, x1, x2, y0, y1, y2;
        x1 = y1 = 0;
        x0 = xyDistans_[0];
        y0 = xyDistans_[1];
        x2 = xyDistans[0];
        y2 = xyDistans[1];
        return ((y1 - y2) * x0 + (x2 - x1) * y0 + x1 * y2 - x2 * y1) / Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));
    }

    public int getRoadTotalLine() {
        return (int) ((getDistanceWithBaseLine(this.latLng2) / LANE_WIDTH) + 0.5);
    }

    public int getRoadLine(LatLng targetLocation) {
        final double dis = getDistanceWithBaseLine(targetLocation);
        return (int) (dis / LANE_WIDTH) + 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getRoadName() == null) return false;
        if (obj == null) return false;
        if (!(obj instanceof RoadInfoHistory)) return false;
        return this.roadName.toUpperCase().equals(((RoadInfoHistory) obj).roadName.toLowerCase());
    }

    /**
     * 计算绕路几车道id信息
     *
     * @param carLocation
     * @return
     */
    public static RoadInfoHistory getRoadInfoHistory(LatLng carLocation) {
        ArrayList<RoadInfoHistory> roadInfoHistoryArrayList = QDApplication.roadInfoHistoryArrayList;
        for (RoadInfoHistory roadInfoHistory : roadInfoHistoryArrayList) {
            if (roadInfoHistory.isContainerV2(carLocation)) {
                return roadInfoHistory;
            }
        }
        return null;
    }


}
