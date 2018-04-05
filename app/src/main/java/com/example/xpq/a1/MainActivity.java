package com.example.xpq.a1;


import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startClick(View view) {

        startActivity(new Intent(MainActivity.this, DoingActivity.class));
    }

    public void proClick(View view) {

        startActivity(new Intent(MainActivity.this, ProActivity.class));
    }

    public void settingClick(View view) {

        startActivity(new Intent(MainActivity.this, SettingActivity.class));
    }

    public void countClick(View view) {
        startActivity(new Intent(MainActivity.this, CountActivity.class));
    }
}
