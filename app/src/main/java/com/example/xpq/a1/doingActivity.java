package com.example.xpq.a1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import java.util.Timer;
import java.util.TimerTask;


public class doingActivity extends AppCompatActivity {


    private TextView tx;
    private int hour=0;
    private int minute=0;
    private int second=0;
    private String h;
    private String m;
    private String s;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doing);
        tx=(TextView)findViewById(R.id.time);
        timer.schedule(task,0,1000);
    }
    public void stopclick(View view){
       timer.cancel();
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Save");//设置对话框的标题
        builder.setMessage("Do you want to save?");//设置对话框的内容
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {  //这个是设置确定按钮

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent record=new Intent(doingActivity.this,hisActivity.class);
                startActivity(record);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {  //取消按钮

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent ret=new Intent(doingActivity.this,MainActivity.class);
                startActivity(ret);
            }
        });
        AlertDialog b=builder.create();
        b.show();  //必须show一下才能看到对话框，跟Toast一样的道理
    }
    Timer timer = new Timer(true);
    TimerTask task = new TimerTask() {

        public void run() {
            runOnUiThread(new Runnable() {                        //执行多线程实现实时更新
                public void run() {
                    second++;
                    if(second>=60)
                    {
                        second=0;
                        minute++;
                    }
                    if(minute>=60)
                    {
                        minute=0;
                        hour++;
                    }
                    if(second<10)
                        s="0"+Integer.toString(second);
                    else
                        s=Integer.toString(second);
                    if(minute<10)
                        m="0"+Integer.toString(minute);
                    else
                        m=Integer.toString(minute);
                    if(hour<10)
                        h="0"+Integer.toString(hour);
                    else
                        h=Integer.toString(hour);
                    tx.setText(h+":"+m+":"+s);
                }
            });
        }
    };
}
