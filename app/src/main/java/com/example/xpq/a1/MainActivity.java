package com.example.xpq.a1;


import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void startclick(View view){
      Intent start=new Intent(MainActivity.this,doingActivity.class);
      startActivity(start);
    }
    public void proclick(View view){
        Intent profile=new Intent(MainActivity.this,proActivity.class);
        startActivity(profile);
    }
    public void settingclick(View view){
        Intent setting=new Intent(MainActivity.this,settingActivity.class);
        startActivity(setting);
    }
    public void countclick(View view){
        Intent count=new Intent(MainActivity.this,countActivity.class);
        startActivity(count);
    }
}
