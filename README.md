# AndroidLocationLib
Android定位，基于百度基础定位sdk封装。可直接使用。

<img src="/device-screenshot.png" alt="Demo Screen Capture" width="296px" height="581px" />

### Useage

**1**. build.gradle 文件中替换你的appkey
```
manifestPlaceholders = [BAIDU_LBS_API_KEY:"D6xB5kxgyxBIQa2tkxmyGvYO"]//your debug app key
```


**2**. 业务定位

```java
        //初始化
        locateHelper = new LocationHelper.Builder(getApplicationContext())
                .setScanSpan(0)
                .setIsNeedLocationDescribe(true).build();

        //设置回调监听
        locateHelper.registerLocationListener(mLocationListener);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if(aBoolean){
                            //开始定位
                            locateHelper.start();
                        }else{
                            Log.e("MainActivity", "watchLocation request locate permission denied ");
                        }
                    }
                });
                
```
