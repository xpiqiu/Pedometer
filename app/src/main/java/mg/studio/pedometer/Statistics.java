package mg.studio.pedometer;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Statistics extends AppCompatActivity {
    private TextView p;
    public LocationClient mLocationClient;
    private MapView mapView;
    private BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLoctionListener());
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.statistics);
        mapView=(MapView)findViewById(R.id.bmapView);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        p = (TextView) findViewById(R.id.po);
        List<String> perimissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(Statistics.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            perimissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(Statistics.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            perimissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(Statistics.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            perimissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        }
        if (!perimissionList.isEmpty()) {
            String[] permissions = perimissionList.toArray(new String[perimissionList.size()]);
            ActivityCompat.requestPermissions(Statistics.this, permissions, 1);
        } else {
            requestLoaction();
        }
    }

    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    private void requestLoaction() {
        initLoation();
        mLocationClient.start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] perimissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLoaction();
                } else {
                    Toast.makeText(this, "weizhi", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    public class MyLoctionListener implements BDLocationListener {

        public void onReceiveLocation(BDLocation bdLocation) {
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude()));
            baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
            locationBuilder.latitude(bdLocation.getLatitude());
            locationBuilder.longitude(bdLocation.getLongitude());
            MyLocationData locationData=locationBuilder.build();
            baiduMap.setMyLocationData(locationData);
        }
    }

    private void initLoation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    protected  void onDestroy(){
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }


}
