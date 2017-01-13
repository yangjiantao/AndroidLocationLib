# AndroidLocationLib
Android定位，基于百度基础定位sdk封装。可直接使用。

<img src="/device-screenshot.png" alt="Demo Screen Capture" width="296px" height="581px" />

###Useage

```java
        locateHelper = new LocationHelper.Builder(getApplicationContext())
                .setScanSpan(0)
                .setIsNeedLocationDescribe(true).build();
                
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
                
```
