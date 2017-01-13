package io.jiantao.android.locate;

import android.content.Context;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * baidu locate service
 *
 * @author Jiantao.Yang
 *         2017/1/12 14:47
 * @version 1.0
 */
public class BDLocationService {

    private LocationClient mClient;
    private LocationClientOption mOption;

    private volatile static BDLocationService sInstance;

    private BDLocationService(Context context) {
        mClient = new LocationClient(context);
        mClient.setLocOption(getDefaultLocationClientOption());
    }

    /**
     * @param context  applicationContext
     * @return
     */
    public static BDLocationService getSingleton(Context context) {
        if (sInstance == null) {
            synchronized (BDLocationService.class) {
                if (sInstance == null) {
                    sInstance = new BDLocationService(context);
                }
            }
        }
        return sInstance;
    }

    public boolean registerLocationListener(BDLocationListener listener){
        boolean isSuccess = false;
        if(listener != null){
            mClient.registerLocationListener(listener);
            isSuccess = true;
        }
        return  isSuccess;
    }

    public void unRegisterLocationListener(BDLocationListener listener){
        if(listener != null){
            mClient.unRegisterLocationListener(listener);
        }
    }

    /**
     * start locate
     */
    public synchronized void start(){
        if(mClient != null && !mClient.isStarted()){
            mClient.start();
        }
    }

    /**
     * stop locate
     */
    public synchronized void stop(){
        if(mClient != null && mClient.isStarted()){
            mClient.stop();
        }
    }

    /***
     *
     * @param option
     * @return isSuccessSetOption
     */
    public boolean setLocationOption(LocationClientOption option){
        boolean isSuccess = false;
        if(option != null){
            if(mClient.isStarted())
                mClient.stop();
            mOption = option;
            mClient.setLocOption(option);
            isSuccess = true;
        }
        return isSuccess;
    }


    public LocationClientOption getDefaultLocationClientOption(){
        if(mOption == null){
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
            mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集

            mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return mOption;
    }

}
