package io.jiantao.android.locate;

import android.content.Context;
import android.support.annotation.IntDef;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 定位辅助类。
 * 对百度定位的封装。
 *
 * @author Jiantao.Yang
 *         2017/1/10 19:17
 * @version 1.0
 */

public class LocationHelper {

    final BDLocationService locationService;
    final LocationClientOption option;
    public LocationHelper(Context context) {
        this(new Builder(context));
    }

    private LocationHelper(Builder builder){
        this.locationService = builder.mLocationService;
        this.option = builder.mOption;
    }

    public boolean registerLocationListener(LocationListener listener) {
        return locationService.registerLocationListener(listener);
    }

    public void unRegisterLocationListener(LocationListener listener) {
        locationService.unRegisterLocationListener(listener);
    }

    public void start() {
        locationService.start();
    }

    public void stop() {
        locationService.stop();
    }

    public static final class Builder {
        public static final int LOCATION_MODE_HIGH_ACCURACY = 1;
        public static final int LOCATION_MODE_BATTERY_SAVING = 2;
        public static final int LOCATION_MODE_DEVICE_SENSORS = 3;

        LocationClientOption mOption;
        BDLocationService mLocationService;
        @IntDef({LOCATION_MODE_HIGH_ACCURACY, LOCATION_MODE_BATTERY_SAVING, LOCATION_MODE_DEVICE_SENSORS})
        @Retention(RetentionPolicy.SOURCE)
        public @interface LocateMode {
        }

        public Builder(Context context) {
            this.mLocationService = BDLocationService.getSingleton(context);
            this.mOption = mLocationService.getDefaultLocationClientOption();
        }

        /**
         * //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
         *
         * @param mode one of {@link #LOCATION_MODE_HIGH_ACCURACY}, {@link #LOCATION_MODE_BATTERY_SAVING} ,or {@link #LOCATION_MODE_DEVICE_SENSORS}
         */
        public Builder setLocationMode(@LocateMode int mode) {
            LocationClientOption.LocationMode bdMode = LocationClientOption.LocationMode.Hight_Accuracy;
            switch (mode){
                case LOCATION_MODE_BATTERY_SAVING:
                    bdMode = LocationClientOption.LocationMode.Battery_Saving;
                    break;
                case LOCATION_MODE_DEVICE_SENSORS:
                    bdMode = LocationClientOption.LocationMode.Device_Sensors;
                    break;
            }
            mOption.setLocationMode(bdMode);
            return this;
        }

        /**
         * 可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
         * @param coorType
         */
        public Builder setCoorType(String coorType) {
            mOption.setCoorType(coorType);
            return this;
        }

        /**
         * 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
         * @param milliseconds
         */
        public Builder setScanSpan(int milliseconds){
            mOption.setScanSpan(milliseconds);
            return this;
        }

        /**
         * 可选，设置是否需要地址信息，默认不需要
         * @param isNeed
         */
        public Builder setIsNeedAddress(boolean isNeed){
            mOption.setIsNeedAddress(isNeed);
            return this;
        }

        /**
         * 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
         * @param isNeed
         */
        public Builder setIsNeedLocationDescribe(boolean isNeed){
            mOption.setIsNeedLocationDescribe(isNeed);
            return this;
        }

        /**
         * 可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
         * @param isNeed
         */
        public Builder setIsNeedAltitude(boolean isNeed){
            mOption.setIsNeedAltitude(isNeed);
            return this;
        }
        /**
         * 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
         * @param isNeed
         */
        public Builder setIsNeedLocationPoiList(boolean isNeed){
            mOption.setIsNeedLocationPoiList(isNeed);
            return this;
        }

        /**
         * 可选，设置是否需要设备方向结果
         * @param isNeed
         */
        public Builder setNeedDeviceDirect(boolean isNeed){
            mOption.setNeedDeviceDirect(isNeed);
            return this;
        }

        /**
         * 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
         * @param isNotify
         */
        public Builder setLocationNotify(boolean isNotify){
            mOption.setLocationNotify(isNotify);
            return this;
        }

        /**
         * 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
         * @param isIgnore
         */
        public Builder setIgnoreKillProcess(boolean isIgnore){
            mOption.setIgnoreKillProcess(isIgnore);
            return this;
        }

        /**
         * 可选，默认false，设置是否收集CRASH信息，默认收集
         * @param isIgnore
         */
        public Builder SetIgnoreCacheException(boolean isIgnore){
            mOption.SetIgnoreCacheException(isIgnore);
            return this;
        }

        public LocationHelper build(){
            return new LocationHelper(this);
        }
    }

    /**
     * 定位信息
     */
    public static class LocationEntity {
        BDLocation bdLocation;

        public LocationEntity(BDLocation bdLocation) {
            this.bdLocation = bdLocation;
        }

        public String getTime() {
            return this.bdLocation.getTime();
        }

        // 纬度
        public double getLatitude() {
            return this.bdLocation.getLatitude();
        }

        // 经度
        public double getLongitude() {
            return this.bdLocation.getLongitude();
        }

        public String getCountryCode() {
            return this.bdLocation.getCountryCode();
        }

        public String getCountry() {
            return this.bdLocation.getCountry();
        }

        // 地址信息
        public String getAddrStr() {
            return this.bdLocation.getAddrStr();
        }

        public String getProvince() {
            return this.bdLocation.getProvince();
        }

        public String getCity() {
            return this.bdLocation.getCity();
        }

        public String getCityCode() {
            return this.bdLocation.getCityCode();
        }

        // 区
        public String getDistrict() {
            return this.bdLocation.getDistrict();
        }

        // 街道
        public String getStreet() {
            return this.bdLocation.getStreet();
        }

        public String getStreetNumber() {
            return this.bdLocation.getStreetNumber();
        }

        public String getLocationDescribe() {
            return this.bdLocation.getLocationDescribe();
        }

        //方向
        public float getDirection() {
            return this.bdLocation.getDirection();
        }

        // GPS定位结果  海拔高度 单位：米
        // 网络定位结果  单位：米
        public double getAltitude() {
            return this.bdLocation.hasAltitude() ? bdLocation.getAltitude() : 0L;
        }

        @Override
        public String toString() {
            if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);
                sb.append("time : ");
                /**
                 * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                 * bdLocation.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                 */
                sb.append(bdLocation.getTime());
                sb.append("\nlocType : ");// 定位类型
                sb.append(bdLocation.getLocType());
                sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                sb.append(bdLocation.getLocTypeDescription());
                sb.append("\nlatitude : ");// 纬度
                sb.append(bdLocation.getLatitude());
                sb.append("\nlontitude : ");// 经度
                sb.append(bdLocation.getLongitude());
                sb.append("\nradius : ");// 半径
                sb.append(bdLocation.getRadius());
                sb.append("\nCountryCode : ");// 国家码
                sb.append(bdLocation.getCountryCode());
                sb.append("\nCountry : ");// 国家名称
                sb.append(bdLocation.getCountry());
                sb.append("\ncitycode : ");// 城市编码
                sb.append(bdLocation.getCityCode());
                sb.append("\ncity : ");// 城市
                sb.append(bdLocation.getCity());
                sb.append("\nDistrict : ");// 区
                sb.append(bdLocation.getDistrict());
                sb.append("\nStreet : ");// 街道
                sb.append(bdLocation.getStreet());
                sb.append("\naddr : ");// 地址信息
                sb.append(bdLocation.getAddrStr());
                sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                sb.append(bdLocation.getUserIndoorState());
                sb.append("\nDirection(not all devices have value): ");
                sb.append(bdLocation.getDirection());// 方向
                sb.append("\nlocationdescribe: ");
                sb.append(bdLocation.getLocationDescribe());// 位置语义化信息
                sb.append("\nPoi: ");// POI信息
                if (bdLocation.getPoiList() != null && !bdLocation.getPoiList().isEmpty()) {
                    for (int i = 0; i < bdLocation.getPoiList().size(); i++) {
                        Poi poi = (Poi) bdLocation.getPoiList().get(i);
                        sb.append(poi.getName() + ";");
                    }
                }
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                    sb.append("\nspeed : ");
                    sb.append(bdLocation.getSpeed());// 速度 单位：km/h
                    sb.append("\nsatellite : ");
                    sb.append(bdLocation.getSatelliteNumber());// 卫星数目
                    sb.append("\nheight : ");
                    sb.append(bdLocation.getAltitude());// 海拔高度 单位：米
                    sb.append("\ngps status : ");
                    sb.append(bdLocation.getGpsAccuracyStatus());// *****gps质量判断*****
                    sb.append("\ndescribe : ");
                    sb.append("gps定位成功");
                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                    // 运营商信息
                    if (bdLocation.hasAltitude()) {// *****如果有海拔高度*****
                        sb.append("\nheight : ");
                        sb.append(bdLocation.getAltitude());// 单位：米
                    }
                    sb.append("\noperationers : ");// 运营商信息
                    sb.append(bdLocation.getOperators());
                    sb.append("\ndescribe : ");
                    sb.append("网络定位成功");
                } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                    sb.append("\ndescribe : ");
                    sb.append("离线定位成功，离线定位结果也是有效的");
                } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                    sb.append("\ndescribe : ");
                    sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                    sb.append("\ndescribe : ");
                    sb.append("网络不同导致定位失败，请检查网络是否通畅");
                } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                    sb.append("\ndescribe : ");
                    sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                }
                return sb.toString();
            }
            return "";
        }
    }

    public static abstract class LocationListener implements BDLocationListener {

        @Override
        public final void onReceiveLocation(BDLocation bdLocation) {
            if (null != bdLocation && bdLocation.getLocType() != BDLocation.TypeServerError) {
                LocationEntity location = new LocationEntity(bdLocation);
                onReceiveLocation(location);
                String errorMsg = null;
                if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                    errorMsg = "服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因";
                } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                    errorMsg = "网络不同导致定位失败，请检查网络是否通畅";
                } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                    errorMsg = "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机";
                }
                if (!TextUtils.isEmpty(errorMsg)) {
                    onError(new Throwable(errorMsg));
                }
            } else {
                onError(new Throwable("uncaught exception"));
            }

        }

        public abstract void onReceiveLocation(LocationEntity location);

        public void onError(Throwable e) {

        }
    }

}
