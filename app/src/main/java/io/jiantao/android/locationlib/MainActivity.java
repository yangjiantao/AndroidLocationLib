package io.jiantao.android.locationlib;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;

import io.jiantao.android.locate.LocationHelper;
import rx.functions.Action1;


public class MainActivity extends AppCompatActivity {

    LocationHelper locateHelper;
    TextView textView;
    private LocationHelper.LocationListener mLocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
        locateHelper = new LocationHelper.Builder(getApplicationContext())
                .setScanSpan(0)
                .setIsNeedLocationDescribe(true).build();
        mLocationListener = new LocationHelper.LocationListener() {
            @Override
            public void onReceiveLocation(LocationHelper.LocationEntity location) {
                System.out.println(location);
                textView.setText(location.toString());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(" throwable "+e);
                textView.setText(e.getMessage());
            }
        };
        locateHelper.registerLocationListener(mLocationListener);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if(aBoolean){
                            locateHelper.start();
                        }else{
                            Log.e("MainActivity", "watchLocation request locate permission denied ");
                        }
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        locateHelper.unRegisterLocationListener(mLocationListener);
        locateHelper.stop();
    }
}
