package mg.studio.pedometer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Calendar;

public class CountActivity extends AppCompatActivity {
    public MainActivity.My dbHelper;

    private LinearLayout lin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        lin = findViewById(R.id.lin);
        dbHelper=new MainActivity.My(CountActivity.this,"use",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("use", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int step = cursor.getInt(cursor.getColumnIndex("step"));
                double km = cursor.getDouble(cursor.getColumnIndex("km"));
                BigDecimal bd = new BigDecimal(km);
                bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
                creatText("Date:" + getdate() + "    " + step + "      " + bd+ "km");
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


    private void creatText(String str) {
        final TextView textView = new TextView(this);
        textView.setPadding(10, 10, 10, 10);
        textView.setText(str);
        textView.setTextSize(25);
        lin.addView(textView);
    }
    private String getdate(){
        Calendar calendar = Calendar.getInstance();//获取系统的日期
        int year = calendar.get(Calendar.YEAR);//年
        int month = calendar.get(Calendar.MONTH);//月
        int day = calendar.get(Calendar.DAY_OF_MONTH);//日
        return year+"-"+(month+1)+"-"+day;
    }
}
