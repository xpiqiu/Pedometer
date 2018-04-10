package mg.studio.pedometer;


import android.content.ContentValues;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.LocationClient;


import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;




public class DoingActivity extends AppCompatActivity {


    private TextView tx;
    private int hour = 0;
    private int minute = 0;
    private int second = 0;
    private int flag = 0;

    private double temp;
    private String h;
    private String m;
    private String s;
    private TextView p;
    private TextView km;
    private TextView sp;
    public LocationClient mLocationClient;
    int step=0;
    private SensorManager mSensorManager;
    private Sensor mStepSensor;
    private int mStep;
    private double distance;
    private MainActivity.My dbHelper;


    private SensorEventListener mSensorEventListener = new SensorEventListener() {

        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        public void onSensorChanged(SensorEvent event) {
            if(judge(event.values[0],12.0)) {
                mStep++;
                double temp = (int) (4 + Math.random() * (6 - 4 + 1)) / 10000.0;
                distance += temp;
            }
            p.setText(mStep+"");
            BigDecimal km2 = new BigDecimal(distance);
            km2 = km2.setScale(3, BigDecimal.ROUND_HALF_UP);
            km.setText(km2+"km");
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
        mStepSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        tx = findViewById(R.id.time);
        p = (TextView) findViewById(R.id.po);
        km = (TextView) findViewById(R.id.km);
        sp = (TextView) findViewById(R.id.speed);
        SharedPreferences sharedPreferences = getSharedPreferences("choose", MODE_PRIVATE);
        temp=Integer.parseInt(sharedPreferences.getString("progress","0"))+20;
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
                dbHelper=new MainActivity.My(DoingActivity.this,"xyz",null,1);
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("date",getdate());
                values.put("step",mStep);
                values.put("km",distance);
                db.insert("xyz",null,values);
                Toast.makeText(DoingActivity.this,"Data has been stored",Toast.LENGTH_SHORT).show();
                Intent ret = new Intent(DoingActivity.this, MainActivity.class);
                startActivity(ret);
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
    private String getdate(){
        Calendar calendar = Calendar.getInstance();//获取系统的日期
        int year = calendar.get(Calendar.YEAR);//年
        int month = calendar.get(Calendar.MONTH);//月
        int day = calendar.get(Calendar.DAY_OF_MONTH);//日
        return year+"-"+(month+1)+"-"+day;
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
                    double t=hour+(minute*60+second)/3600.0;
                    double spe=distance/t;
                    BigDecimal speed = new BigDecimal(spe);
                    speed = speed.setScale(2, BigDecimal.ROUND_HALF_UP);
                    sp.setText(speed+"km/h");
                }
            });
        }
    };


    private boolean judge(double a,double b){
        return (a>=b&&a<=temp)||(a<=-b&&a>=-temp);
    }
}
