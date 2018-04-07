package mg.studio.pedometer;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class DoingActivity extends AppCompatActivity {


    private TextView tx;
    private int hour = 0;
    private int minute = 0;
    private int second = 0;
    private String h;
    private String m;
    private String s;
    private TextView p;
    public LocationClient mLocationClient;
    int step=0;
    private SensorManager mSensorManager;
    private Sensor mStepSensor;



    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        private int mStep;
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        public void onSensorChanged(SensorEvent event) {
            if (event.values[0] <= 1.0f) {
                mStep++;
            }
            p.setText(mStep+"");
        }
    };

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorEventListener, mStepSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(mSensorEventListener);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mStepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        tx = findViewById(R.id.time);
        p = (TextView) findViewById(R.id.po);
        timer.schedule(task, 0, 1000);

    }



    public void bntStop(View view) {
        timer.cancel();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save");//设置对话框的标题
        builder.setMessage("Do you want to save?");//设置对话框的内容
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent record = new Intent(DoingActivity.this, Statistics.class);
                startActivity(record);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {  //取消按钮

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent ret = new Intent(DoingActivity.this, MainActivity.class);
                startActivity(ret);
            }
        });
        AlertDialog b = builder.create();
        b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
    }

    Timer timer = new Timer(true);
    TimerTask task = new TimerTask() {

        public void run() {
            runOnUiThread(new Runnable() {                        //执行多线程实现实时更新
                public void run() {
                    second++;
                    if (second >= 60) {
                        second = 0;
                        minute++;
                    }
                    if (minute >= 60) {
                        minute = 0;
                        hour++;
                    }
                    if (second < 10)
                        s = "0" + Integer.toString(second);
                    else
                        s = Integer.toString(second);
                    if (minute < 10)
                        m = "0" + Integer.toString(minute);
                    else
                        m = Integer.toString(minute);
                    if (hour < 10)
                        h = "0" + Integer.toString(hour);
                    else
                        h = Integer.toString(hour);
                    tx.setText(h + ":" + m + ":" + s);
                }
            });
        }
    };
}
